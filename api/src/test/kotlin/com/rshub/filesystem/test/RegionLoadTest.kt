package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test

class RegionLoadTest {

    @Test
    fun `load region edgeville`() {
        val edge = CacheHelper.getRegion(12342)

        val start = WorldTile( 3079, 3496, 0)
        val target = WorldTile(3079, 3495, 0)

        val tile = WorldTile(3079, 3496, 0)

        val localX = tile.getXInRegion()
        val localY = tile.getYInRegion()

        println(localX)
        println(localY)


        val t = edge.objects.filter { it.localTile.x == localX && it.localTile.y == localY && it.localTile.z == 0 }
        for (t in t) {
            if (t != null) {
                val def = CacheHelper.getObject(t.objectId)
                println(t)
                println(def.name)
                println(def.blocksProjectile)
            }
        }
    }

}