package com.rshub.javafx.test

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.web.Graph.Companion.toWeb
import com.rshub.definitions.maps.WorldTile
import com.rshub.javafx.DebugUI.Companion.fxModule
import org.koin.core.context.startKoin

object ApplicationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        /*startKoin {
            modules(fxModule)
        }*/

        //launch<DebugUI>()

        WalkHelper.loadWeb()

        val pos = WorldTile(2942, 3354, 0)
        val dest = WorldTile(2957, 3296, 0)

        val graph = WalkHelper.getGraph()
        val possibleStarts = graph.getAllVertices().filter { it.tile.distance(pos) < 63 }
        val web = WalkHelper.getGraph().toWeb()

        val start = possibleStarts.minByOrNull { it.tile.distance(pos) } ?: kotlin.run {
            println("failed.")
            null
        }
        val end = graph.getAllVertices().minByOrNull { it.tile.distance(dest) }!!

        if (start != null) {
            println(start)
            println(end)

            val path = web.findPath(start, end)

            path.first.forEach {
                println(it.vertex.tile)
                println(it.edge.strategy::class.java.simpleName)
                println()
            }

            println(web.edges.size)
        }

    }

}