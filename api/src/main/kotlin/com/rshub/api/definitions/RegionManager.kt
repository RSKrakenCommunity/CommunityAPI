package com.rshub.api.definitions

import com.rshub.api.map.RenderFlag
import com.rshub.definitions.maps.MapTilesDefinition
import com.rshub.definitions.maps.ObjectTilesDefinition
import com.rshub.definitions.maps.loaders.MapTilesLoader
import com.rshub.definitions.maps.loaders.ObjectTilesLoader
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class RegionManager(val fs: Filesystem) {

    private val tilesLoader = MapTilesLoader()

    fun load(regionId: Int) : Pair<MapTilesDefinition?, ObjectTilesDefinition?> {
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        val maps = fs.getReferenceTable(5)
        if (maps != null) {
            val mapArchive = maps.loadArchive(CacheHelper.getMapArchiveId(regionX, regionY))
            if(mapArchive != null) {
                println(mapArchive.files.size)
                val objs = mapArchive.files[0]
                val tiles = mapArchive.files[2]
                val tilesDef = if(tiles != null) {
                    tilesLoader.load(regionId, ByteBuffer.wrap(tiles.data))
                } else null
                val objsDef = if(objs != null) {
                    val loader = ObjectTilesLoader(tilesDef)
                    loader.load(regionId, ByteBuffer.wrap(objs?.data ?: byteArrayOf()))
                } else null
                return tilesDef to objsDef
            }
        }
        return null to null
    }

}