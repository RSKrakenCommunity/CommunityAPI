package com.rshub.javafx.ui.tabs.containers

import com.rshub.api.definitions.CacheHelper
import com.rshub.javafx.ui.model.RSItemModel
import com.rshub.javafx.ui.model.containers.ContainerFilterModel
import com.rshub.javafx.ui.model.containers.ContainerModel
import javafx.beans.binding.Bindings
import tornadofx.*

class ContainerFilterFragment : Fragment("Container Debug") {

    private val model: ContainerFilterModel by di()

    override val root = hbox {
        spacing = 10.0
        listview<ContainerModel> {
            items.bind(model.containers) { _, it -> it }
            bindSelected(model.selectedContainer)
            model.selectedContainer.onChange {
                model.items.clear()
                if(it != null) {
                    model.items.setAll(it.container.get().items.map { i -> RSItemModel(CacheHelper.getItem(i.id), i.amount) })
                }
            }
            cellFormat { item ->
                textProperty().bind(Bindings.format("Container ID - %d", item.container.get().id))
            }
        }
        vbox {
            spacing = 5.0
            vbox {
                dynamicContent(model.selectedContainer) {
                    if (it != null) {
                        form {
                            fieldset("Container Statistics") {
                                field("Size") {
                                    label("${it.size}")
                                }
                            }
                        }
                    } else {
                        form {
                            fieldset {
                                field {
                                    label("Select an Item Container.")
                                }
                            }
                        }
                    }
                }
            }
            separator()
            vbox {
                dynamicContent(model.selectedItem) {
                    if (it != null) {
                        add(it.toForm())
                    }
                }
            }
        }
        listview(model.items) {
            bindSelected(model.selectedItem)
            cellFormat {
                textProperty().bind(Bindings.format("[%d, %d] - %s", it.itemId, it.amount, it.name))
                tooltip = tooltip { textProperty().bind(Bindings.format("%d", it.amount)) }
            }
        }
    }
}