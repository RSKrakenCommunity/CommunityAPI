package com.rshub.services

import com.rshub.api.services.GameStateService
import com.rshub.definitions.maps.WorldTile.Companion.localX
import com.rshub.definitions.maps.WorldTile.Companion.toLocal
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.GlobalModel
import com.rshub.javafx.ui.model.PlayerModel
import kraken.plugin.api.Debug
import kraken.plugin.api.Kraken
import kraken.plugin.api.Players
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.runLater

class PlayerUpdateService : GameStateService, KoinComponent {

    private val model: PlayerModel by inject()
    private val global: GlobalModel by inject()

    override suspend fun stateChanged() {
        val player = Players.self()
        if(player != null) {
            runLater {
                if (model.tile.get() != player.globalPosition.toTile().toString()) {
                    model.tile.set(player.globalPosition.toTile().toString())
                    model.localTile.set(player.globalPosition.toLocal().toString())
                }
                if (model.serverIndex.get() != player.serverIndex) {
                    model.serverIndex.set(player.serverIndex)
                }
                if (model.health.get() != player.health) {
                    model.health.set(player.health)
                }
                if (model.adrenaline.get() != player.adrenaline) {
                    model.adrenaline.set(player.adrenaline)
                }
                if (model.combatLevel.get() != player.combatLevel) {
                    model.combatLevel.set(player.combatLevel)
                }
                if (model.totalLevel.get() != player.totalLevel) {
                    model.totalLevel.set(player.totalLevel)
                }
            }
        }
    }
}