package com.rshub

import com.rshub.api.pathing.WalkHelper
import com.rshub.api.pathing.WebWalkerSerializer
import com.rshub.api.services.GameStateHelper
import com.rshub.javafx.DebugUI
import com.rshub.javafx.ui.model.walking.LocationModel
import com.rshub.services.PlayerUpdateService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.pf4j.Plugin
import org.pf4j.PluginWrapper
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import kotlin.io.path.exists

class DeveloperPlugin(wrapper: PluginWrapper?) : Plugin(wrapper) {

    private val errorPath = Paths.get("error.txt")
    private val errorOutput = PrintStream(FileOutputStream(errorPath.toFile()))

    override fun start() {
        if (!errorPath.exists()) {
            errorPath.toFile().createNewFile()
        }
        System.setOut(errorOutput)
        System.setErr(errorOutput)
        startKoin {
            modules(DebugUI.fxModule)
        }
        GameStateHelper.registerService(PlayerUpdateService())
        GlobalScope.launch {
            WalkHelper.loadWeb()
            LocationModel.load(Paths.get(System.getProperty("user.home")).resolve("kraken-plugins"))
            tornadofx.launch<DebugUI>()
        }
    }
}