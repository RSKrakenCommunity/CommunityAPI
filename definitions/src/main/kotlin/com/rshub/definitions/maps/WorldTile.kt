package com.rshub.definitions.maps

import kraken.plugin.api.Vector3i

class WorldTile(x: Int, y: Int, z: Int) : Vector3i(x, y, z) {
    val regionX: Int get() = x shr 6
    val regionY: Int get() = y shr 6
    val regionId: Int get() = (regionX shl 8) + regionY
    fun getXInRegion(): Int {
        return x and 0x3F
    }
    fun getYInRegion(): Int {
        return y and 0x3F
    }
}