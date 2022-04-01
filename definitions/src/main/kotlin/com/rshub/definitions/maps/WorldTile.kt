package com.rshub.definitions.maps


import kotlinx.serialization.Serializable
import kraken.plugin.api.Vector3i

@Serializable(with = WorldTileSerializer::class)
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

    companion object {
        fun tile(x: Int, y: Int, z: Int = 0) = WorldTile(x, y, z)
        fun Vector3i.toTile() = WorldTile(x, y, z)
        fun Vector3i.expand(by: Int) = expand(Vector3i(by, by, z))
        val Vector3i.regionX get() = x shr 6
        val Vector3i.regionY get() = y shr 6
        val Vector3i.regionId get() = (regionX shl 8) + regionY
        val Vector3i.localX get() = x and 0x3f
        val Vector3i.localY get() = y and 0x3f
    }
}