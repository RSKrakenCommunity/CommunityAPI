package com.rshub.api.entities.spirits.player

import com.rshub.api.entities.spirits.WorldSpirit
import kraken.plugin.api.Player
import kraken.plugin.api.Vector2
import kraken.plugin.api.Vector3
import kraken.plugin.api.Vector3i

class WorldPlayer(val player: Player) : WorldSpirit {

    override val globalPosition: Vector3i
        get() = player.globalPosition
    override val activeStatusBars: Map<Int, Boolean>
        get() = player.activeStatusBars
    override val animationId: Int
        get() = player.animationId
    override val scenePosition: Vector3
        get() = player.scenePosition
    override val directionOffset: Vector2
        get() = player.directionOffset
    override val interactingIndex: Int
        get() = player.interactingIndex
    override val isAnimationPlaying: Boolean
        get() = player.isAnimationPlaying
    override val isMoving: Boolean
        get() = player.isMoving
    override val serverIndex: Int
        get() = player.serverIndex
    override val statusBarFill: Map<Int, Float>
        get() = player.statusBarFill

}