package com.rshub.api.entities.spirits

import com.rshub.api.entities.WorldEntity
import kraken.plugin.api.Vector2

interface WorldSpirit : WorldEntity {

    val serverIndex: Int
    val isMoving: Boolean
    val activeStatusBars: Map<Int, Boolean>
    val statusBarFill: Map<Int, Float>
    val animationId: Int
    val isAnimationPlaying: Boolean
    val interactingIndex: Int
    val directionOffset: Vector2

}