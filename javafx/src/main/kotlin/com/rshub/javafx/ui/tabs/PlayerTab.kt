package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.model.PlayerModel
import javafx.beans.binding.Bindings
import tornadofx.*

class PlayerTab : Fragment("Local Player") {

    private val playerModel: PlayerModel by di()

    override val root = form {
        spacing = 10.0
        paddingAll = 10.0

        fieldset("Player Statistics") {
            field {
                label(playerModel.tile)
            }
            field("Server Index") {
                label(playerModel.serverIndex)
            }
            field("Health") {
                label(playerModel.health)
            }
            field("Adrenaline") {
                label(playerModel.adrenaline)
            }
            field("Total Level") {
                label(playerModel.totalLevel)
            }
        }
    }
}