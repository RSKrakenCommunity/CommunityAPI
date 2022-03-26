package com.rshub.api.entities

import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.Vector3
import kraken.plugin.api.Vector3i

interface WorldEntity {

    val scenePosition: Vector3
    val globalPosition: Vector3i

    fun getTile() : WorldTile {
        return WorldTile(globalPosition.x, globalPosition.y, globalPosition.z)
    }

}