package com.rshub.javafx.test

import com.rshub.api.lodestone.Lodestones
import com.rshub.definitions.maps.WorldTile.Companion.tile
import org.junit.jupiter.api.Test

class LodestoneTest {

    @Test
    fun `lodestone test`() {

        val plr = tile(2877, 3417)
        val dest = tile(3186, 3437)

        val plrDist = plr.distance(dest)
        val lodestone = Lodestones.LODESTONES.minByOrNull { it.dest.distance(dest) }
        val lodeDist = lodestone?.dest?.distance(dest) ?: Int.MAX_VALUE

        if(lodeDist < plrDist) {
            println(lodestone)
        } else {
            println("Walk there")
        }


    }

}