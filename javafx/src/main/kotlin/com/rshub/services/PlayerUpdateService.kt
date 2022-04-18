package com.rshub.services

import com.rshub.api.services.GameStateService
import com.rshub.api.variables.Variables
import com.rshub.api.world.PlayerHelper
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile.Companion.toLocal
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.javafx.ui.model.GlobalModel
import com.rshub.javafx.ui.model.PlayerModel
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
                if(model.animationId.get() != player.animationId) {
                    model.animationId.set(player.animationId)
                }
                if(model.isMoving.get() != player.isMoving) {
                    model.isMoving.set(player.isMoving)
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
                if(model.interactingIndex.get() != player.interactingIndex) {
                    model.interactingIndex.set(player.interactingIndex)
                }
                if(model.interacting.get() != player.interacting) {
                    model.interacting.set(player.interacting)
                }
                val inCombat: Boolean by Variables.IN_COMBAT
                if(model.inCombat.get() != inCombat) {
                    model.inCombat.set(inCombat)
                }
                model.attackingSpirit.set(-1)
                model.attackingSpirit.set(PlayerHelper.getAttackingNpc()?.firstOrNull()?.serverIndex ?: -1)
            }
        }
    }
}