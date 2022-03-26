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
    fun transform(x: Int, y: Int): WorldTile {
        val tile = WorldTile(this.x, this.y, z);
        tile.add(x, y, 0)
        return tile
    }

    override fun toString(): String {
        return "Tile($x, $y, $z) - Region $regionId"
    }
}