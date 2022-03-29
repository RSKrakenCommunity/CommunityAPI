package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.model.PlayerModel
import javafx.beans.binding.Bindings
import tornadofx.Fragment
import tornadofx.label
import tornadofx.paddingAll
import tornadofx.vbox

class PlayerTab : Fragment("Local Player") {

    private val playerModel: PlayerModel by di()

    override val root = vbox {
        spacing = 10.0
        paddingAll = 10.0
        label(playerModel.tile)
        label {
            textProperty().bind(Bindings.createStringBinding({
                "Server Index ${playerModel.serverIndex.get()}"
            }, playerModel.serverIndex))
        }
        label {
            textProperty().bind(Bindings.createStringBinding({
                "Health: ${playerModel.health.get()}"
            }, playerModel.health))
        }
        label {
            textProperty().bind(Bindings.createStringBinding({
                "Adrenaline: ${playerModel.adrenaline.get()}"
            }, playerModel.adrenaline))
        }
        label {
            textProperty().bind(Bindings.createStringBinding({
                "Total Level: ${playerModel.totalLevel.get()}"
            }, playerModel.totalLevel))
        }
    }
}