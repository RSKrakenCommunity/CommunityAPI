package com.rshub.javafx.test

import com.rshub.javafx.DebugUI
import com.rshub.javafx.DebugUI.Companion.fxModule
import org.koin.core.context.startKoin
import tornadofx.launch

object ApplicationTest {

    @JvmStatic
    fun main(args: Array<String>) {

        startKoin {
            modules(fxModule)
        }

        launch<DebugUI>()

    }

}