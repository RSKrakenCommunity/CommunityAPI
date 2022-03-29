package com.rshub.javafx.ui.cells

import com.rshub.api.definitions.CacheHelper
import com.rshub.javafx.ui.model.VariableModel
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.paint.Color
import javafx.util.Duration
import kraken.plugin.api.Client
import tornadofx.ChangeListener
import tornadofx.keyframe
import tornadofx.timeline

class VariableCell : TreeCell<VariableModel>() {

    private val redAnim = timeline {
        isAutoReverse = false
        keyframe(Duration.ZERO) {
            keyvalue(textFillProperty(), Color.RED)
        }
        keyframe(Duration.seconds(2.5)) {
            keyvalue(textFillProperty(), Color.BLACK)
        }
    }

    val listener = ChangeListener<Number> { _, _, newValue ->
        if(item.variableId.get() == -1) return@ChangeListener
        text = if (item.isVarp.get()) {
            val varbits = CacheHelper.findVarbitsFor(item.variableId.get())
            if(varbits.isEmpty()) {
                item.displayText.concat(" - ").concat(newValue).get()
            } else {
                item.displayText.get()
            }
        } else {
            redAnim.play()
            val value = CacheHelper.getVarbitValue(item.variableId.get())
            item.displayText.concat(" - ").concat(value).get()
        }
        treeItem
    }

    override fun updateItem(item: VariableModel?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item != null && !empty) {
            item.value.addListener(listener)
            if (item.variableId.get() == -1) {
                text = item.displayText.get()
            } else if (item.isVarp.get()) {
                handleVarp(item)
            } else {
                handleVarbit(item)
            }
        } else {
            if (textProperty().isBound) {
                textProperty().unbind()
            }
            text = null
            graphic = null
        }
    }

    private fun handleVarp(item: VariableModel) {
        val varbits = CacheHelper.findVarbitsFor(item.variableId.get())
        if (varbits.isEmpty()) {
            val conVar = Client.getConVarById(item.variableId.get())
            text = if (conVar != null) {
                item.displayText.concat(" - ").concat(conVar.valueInt).get()
            } else {
                item.displayText.get()
            }
        } else {
            text = item.displayText.get()
            treeItem.children.setAll(varbits.map {
                TreeItem(
                    VariableModel(
                        it.id,
                        "Varbit ${it.id}",
                        CacheHelper.getVarbitValue(it.id),
                        false
                    )
                )
            })
        }
    }

    private fun handleVarbit(item: VariableModel) {
        val value = CacheHelper.getVarbitValue(item.variableId.get())
        text = item.displayText.concat(" - ").concat(value).get()
    }
}