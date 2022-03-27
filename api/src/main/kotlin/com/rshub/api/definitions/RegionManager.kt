package com.rshub.api.definitions

import com.rshub.definitions.maps.RegionDefinition
import com.rshub.definitions.maps.loaders.RegionLoader
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class RegionManager(val fs: Filesystem) {

    private val regionLoader = RegionLoader()
    private val regions = mutableMapOf<Int, RegionDefinition>()

    fun load(regionId: Int) : RegionDefinition {
        if(regions.containsKey(regionId)) return regions[regionId]!!
        val regionX = regionId shr 8
        val regionY = regionId and 0xff
        val maps = fs.getReferenceTable(5)
        if (maps != null) {
            val mapArchive = maps.loadArchive(CacheHelper.getMapArchiveId(regionX, regionY))
            if(mapArchive != null) {
                val objs = mapArchive.files[0]
                val tiles = mapArchive.files[3]
                val tilesData = if(tiles != null) {
                    ByteBuffer.wrap(tiles.data)
                } else null
                val objsData = if(objs != null) {
                    ByteBuffer.wrap(objs.data)
                } else null
                val def = regionLoader.load(regionId, objsData, tilesData)
                regions[regionId] = def
                return def
            }
        }
        return regions.getOrPut(regionId) { regionLoader.newDefinition(regionId) }
    }

}