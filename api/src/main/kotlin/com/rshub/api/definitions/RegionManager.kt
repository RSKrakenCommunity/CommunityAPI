package com.rshub.api.definitions

import com.rshub.definitions.maps.MapTilesDefinition
import com.rshub.definitions.maps.ObjectTilesDefinition
import com.rshub.definitions.maps.loaders.MapTilesLoader
import com.rshub.definitions.maps.loaders.ObjectTilesLoader
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class RegionManager(val fs: Filesystem) {

    private val tilesLoader = MapTilesLoader()

    fun load(regionId: Int) : Pair<MapTilesDefinition, ObjectTilesDefinition>? {
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        val maps = fs.getReferenceTable(5)
        if (maps != null) {
            val mapArchive = maps.loadArchive(regionX or regionY shl 7)
            if(mapArchive != null) {
                val objs = mapArchive.files[0]
                val tiles = mapArchive.files[3]
                val mapTiles = tilesLoader.load(regionId, ByteBuffer.wrap(tiles?.data ?: byteArrayOf()))
                val loader = ObjectTilesLoader(mapTiles)
                val objTiles = loader.load(regionId, ByteBuffer.wrap(objs?.data ?: byteArrayOf()))
                return mapTiles to objTiles
            }
        }
        return null
    }

}