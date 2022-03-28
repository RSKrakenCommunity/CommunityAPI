package com.rshub.javafx.ui.tabs

import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.PlayerModel
import kraken.plugin.api.Players
import tornadofx.Fragment
import tornadofx.button
import tornadofx.label
import tornadofx.vbox

class PlayerTab : Fragment("Local Player") {

    private val playerModel: PlayerModel by inject()

    override val root = vbox {
        spacing = 10.0
        label(playerModel.tile)
        button("Update").setOnAction {
            val player = Players.self()
            if(player != null) {
                playerModel.tile.set(player.globalPosition.toTile().toString())
            }
        }
    }
}