package com.rshub.javafx

import com.rshub.javafx.ui.DebugView
import com.rshub.javafx.ui.model.*
import javafx.stage.Stage
import kraken.plugin.api.Kraken
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import kotlin.reflect.KClass

class DebugUI : App(DebugView::class), KoinComponent {

    override fun init() {
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return GlobalContext.get().get(type)
            }
        }
    }

    override fun start(stage: Stage) {
        stage.title = Kraken.getEmail()
        stage.setOnCloseRequest {
            it.consume()
        }
        super.start(stage)
    }

    companion object {
        val fxModule = module {
            single { GlobalModel() }
            single { PlayerModel() }
            single { VariableDebuggerModel() }
            single { VariableScanModel() }
        }
    }
}