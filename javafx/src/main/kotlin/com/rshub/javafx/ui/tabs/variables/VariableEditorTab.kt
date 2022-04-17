package com.rshub.javafx.ui.tabs.variables

import com.rshub.javafx.ui.model.VariableEditModel
import com.rshub.javafx.ui.model.VariableEditorModel
import javafx.geometry.Orientation
import javafx.util.StringConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tornadofx.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

class VariableEditorTab : Fragment("Variable Editor") {

    private val model: VariableEditorModel by di()

    override val root = vbox {
        loadVariables()
        hbox {
            button("Save Variables") {
                disableWhen(model.variables.emptyProperty())
                setOnAction {
                    val path = Paths.get(System.getProperty("user.home"))
                        .resolve("kraken-plugins")
                    if(!path.exists()) {
                        path.toFile().mkdirs()
                    }
                    val file = path.resolve("variables.json")
                    val data = Json.encodeToString(model.variables.toList())
                    Files.write(file, data.toByteArray())
                }
            }
        }
        hbox {
            listview<VariableEditModel> {
                items.bind(model.variables) { it }
                bindSelected(model.selectedVariable)
                cellFormat {
                    graphic = form {
                        fieldset {
                            field {
                                label(it.variableName)
                            }
                            field("ID") {
                                label(it.variableId)
                            }
                            field("Type") {
                                label(it.variableType)
                            }
                        }
                    }
                }
            }
            separator(Orientation.VERTICAL)
            form {
                fieldset {
                    field("Variable ID") {
                        textfield(model.varId) {
                            stripNonInteger()
                        }
                    }
                    field("Variable Name") {
                        textfield(model.varName)
                    }
                    field("Variable Type") {
                        choicebox(model.type) {
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
                        button("Add New Variable") {
                            disableWhen(model.varName.isEmpty.or(model.varId.isEqualTo(-1)))
                            setOnAction {
                                val name = model.varName.get()
                                val id = model.varId.get()
                                val type = model.type.get()
                                val vem = VariableEditModel(name, id, type)
                                model.variables.add(vem)
                            }
                        }
                        button("Delete Variable") {
                            disableWhen(model.selectedVariable.isNull)
                            setOnAction {
                                val sel = model.selectedVariable.get()
                                model.variables.remove(sel)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadVariables() {
        try {
            val con = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/variables.json")
            val stream = String(con.openStream().readBytes())
            val list = Json.decodeFromString<List<VariableEditModel>>(stream)
            model.variables.clear()
            model.variables.addAll(list)
        } catch (_: Exception) {
            val path = Paths.get(System.getProperty("user.home"))
                .resolve("kraken-plugins")
                .resolve("variables.json")
            if(path.exists()) {
                val data = String(Files.readAllBytes(path))
                val list = Json.decodeFromString<List<VariableEditModel>>(data)
                model.variables.clear()
                model.variables.addAll(list)
            }
        }
    }

}