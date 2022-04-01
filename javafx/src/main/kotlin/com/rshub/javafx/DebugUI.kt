package com.rshub.javafx

import com.rshub.api.pathing.WebWalkerSerializer
import com.rshub.javafx.ui.DebugView
import com.rshub.javafx.ui.model.GlobalModel
import com.rshub.javafx.ui.model.PlayerModel
import com.rshub.javafx.ui.model.VariableDebuggerModel
import com.rshub.javafx.ui.model.VariableScanModel
import com.rshub.javafx.ui.model.walking.LocationModel
import com.rshub.javafx.ui.model.walking.VertexEditorModel
import com.rshub.javafx.ui.model.walking.WalkingModel
import com.rshub.javafx.ui.model.walking.WebWalkingModel
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
        stage.isResizable = false
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
            single { WebWalkingModel() }
            single { VertexEditorModel() }
            single { WalkingModel() }
        }
    }
}