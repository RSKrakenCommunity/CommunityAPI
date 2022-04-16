package com.rshub.javafx.ui.tabs.variables

import com.rshub.api.definitions.CacheHelper
import com.rshub.javafx.ui.model.VariableDebuggerModel
import com.rshub.javafx.ui.model.VariableModel
import com.rshub.javafx.ui.model.VariableScanModel
import kraken.plugin.api.Client
import tornadofx.*

class VariableDebuggerTab : Fragment("Variable Debugger") {

    private val model: VariableDebuggerModel by di()
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