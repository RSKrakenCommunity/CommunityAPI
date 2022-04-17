package com.rshub.javafx.ui.tabs.variables

import com.rshub.api.definitions.CacheHelper
import com.rshub.javafx.ui.model.*
import javafx.util.StringConverter
import kraken.plugin.api.Client
import tornadofx.*

class VariableDebuggerTab : Fragment("Variable Debugger") {

    private val model: VariableDebuggerModel by di()
    private val veditor: VariableEditorModel by di()
    private val scanner: VariableScanModel by di()

    override val root = borderpane {
        model.selectedVarp.onChange {
            if (it != null && it.variableId.get() > -1) {
                if (model.varbits.isBound) {
                    model.varbits.unbind()
                }
                if (it.isVarp.get()) {
                    it.varbits.setAll(
                        CacheHelper.findVarbitsFor(it.variableId.get())
                            .map { def -> VariableModel(def.id, "", CacheHelper.getVarbitValue(def.id), false) })
                } else if (it.varbits.isNotEmpty()) {
                    it.varbits.clear()
                }
                model.varbits.bind(it.varbits)
            }
        }
        center {
            form {
                fieldset {
                    fieldset {
                        button("Clear Variables") {
                            disableWhen(model.varps.emptyProperty())
                            setOnAction {
                                model.varps.clear()
                            }
                        }
                    }
                    field("Scan Mode") {
                        label(model.isScanMode)
                    }
                    field {
                        button("Reset Scan") {
                            enableWhen(model.isScanMode)
                            setOnAction {
                                model.isScanMode.set(false)
                                model.varps.clear()
                            }
                        }
                    }
                    field {
                        button("Scan") {
                            setOnAction {
                                model.isScanMode.set(true)
                                val values = scanner.scan()
                                model.varps.clear()
                                model.varps.putAll(values.map { VariableModel(it.variableId.get(), "", it.value.get(), it.isVarp.get()) }.associateBy { it.variableId.get() })
                            }
                        }
                        textfield(scanner.scanValue) {
                            disableWhen(scanner.isValueUnknown)
                            stripNonInteger()
                            promptText = "Enter value"
                        }
                    }
                    field("Unknown Value") {
                        checkbox(property = scanner.isValueUnknown)
                    }
                }
                fieldset("Variable Labeler") {
                    field("Name") {
                        textfield(veditor.varName)
                    }
                    field("Variable Type") {
                        choicebox(veditor.type) {
                            items.addAll(VariableEditModel.Type.values())
                            selectionModel.select(VariableEditModel.Type.VARP)
                            converter = object : StringConverter<VariableEditModel.Type>() {
                                override fun toString(type: VariableEditModel.Type): String {
                                    return type.name.lowercase()
                                        .capitalize()
                                        .replace('_', ' ')
                                        .trim()
                                }

                                override fun fromString(string: String): VariableEditModel.Type {
                                    return VariableEditModel.Type.valueOf(
                                        string.uppercase().replace(' ', '_')
                                    )
                                }
                            }
                        }
                    }
                    field {
                        button("Add Variable") {
                            disableWhen(veditor.varName.isEmpty)
                            setOnAction {
                                val name = veditor.varName.get()
                                val model = when(veditor.type.get()) {
                                    VariableEditModel.Type.VARBIT -> {
                                        val sel = model.selectedVarbit.get()
                                        VariableEditModel(name, sel.variableId.get(), veditor.type.get())
                                    }
                                    VariableEditModel.Type.VARP -> {
                                        val sel = model.selectedVarp.get()
                                        VariableEditModel(name, sel.variableId.get(), veditor.type.get())
                                    }
                                }
                                veditor.variables.add(model)
                            }
                        }
                    }
                }
            }
        }
        left {
            tableview<VariableModel> {
                smartResize()
                bindSelected(model.selectedVarp)
                items.bind(model.varps) { _, v -> v }
                column<VariableModel, Number>("Varp") {
                    it.value.variableId
                }
                column<VariableModel, Number>("Value") {
                    it.value.value
                }
            }
        }
        right {
            tableview<VariableModel> {
                smartResize()
                items.bind(model.varbits) { v -> v }
                bindSelected(model.selectedVarbit)
                column<VariableModel, Number>("Varbit") {
                    it.value.variableId
                }
                column<VariableModel, Number>("Value") {
                    it.value.value
                }
            }
        }
        bottom {
            tableview<VariableModel> {
                maxHeight = 160.0
                smartResize()
                column<VariableModel, Boolean>("Variable Type") {
                    it.value.isVarp
                }.cellFormat {
                    text = if (it) "Varp" else "Varbit"
                }
                column<VariableModel, Number>("Variable ID") {
                    it.value.variableId
                }
                column<VariableModel, Boolean>("Variable Value") {
                    it.value.isVarp
                }.cellFormat {
                    text = if (it) {
                        Client.getConVarById(rowItem.variableId.get()).valueInt.toString()
                    } else {
                        CacheHelper.getVarbitValue(rowItem.variableId.get()).toString()
                    }
                }
            }
        }
    }
}