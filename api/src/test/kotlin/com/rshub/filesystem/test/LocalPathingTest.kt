package com.rshub.filesystem.test

import com.rshub.api.map.ClipFlag
import com.rshub.api.map.Region
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test

class LocalPathingTest {

    @Test
    fun `local pathing test`() {
        /*
        3079 3494
        3079 3491


        3086, 3496, 0
        3086, 3495, 0


        3233, 3287, 0
         */
        val start = WorldTile( 3086, 3496, 0)
        val target = WorldTile(3086, 3495, 0)

        println("${start.regionId} - ${target.regionId}")

        val strat = FixedTileStrategy(target)
        val route = LocalPathing.findLocalRoute(start, 1, strat, false)


        //assert(route != null) { "Failed to find route." }
        println(route)

    }

    @Test
    fun `region clip data`() {
        val edge = Region.get(12342)
        val cur = WorldTile(3079, 3493, 0)
        val masks = edge.clipMap.masks[0]

        val mask = masks[cur.getXInRegion()][cur.getYInRegion()]
        println("${cur.getXInRegion()}, ${cur.getYInRegion()} - $mask")

    }

    @Test
    fun `region x calc`() {
        val regionId = 12342
        val regionX = regionId shr 8
        val regionY = regionId and 0xff

        println(regionX * 64)
        println(regionY * 64)

    }

}