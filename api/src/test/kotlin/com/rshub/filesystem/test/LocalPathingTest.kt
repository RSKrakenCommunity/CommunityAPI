package com.rshub.filesystem.test

import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.strategy.FixedTileStrategy
import com.rshub.definitions.maps.WorldTile
import org.junit.jupiter.api.Test

class LocalPathingTest {

    @Test
    fun `local pathing test`() {
        /*
        3233 3221
        3214 3376
         */
        val start = WorldTile(3094, 3491, 0)
        val target = WorldTile(3079, 3495, 0)
        val strat = FixedTileStrategy(target)
        val route = LocalPathing.findLocalRoute(start, 1, strat, false)

        println(route)

    }

}