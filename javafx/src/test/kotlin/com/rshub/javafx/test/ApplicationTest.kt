package com.rshub.javafx.test

import com.rshub.api.pathing.WalkHelper
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

        println(WalkHelper.getGraph().getAllVertices().size)

    }

}