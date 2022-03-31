package com.rshub

import com.rshub.api.services.GameStateHelper
import com.rshub.javafx.DebugUI
import com.rshub.services.PlayerUpdateService
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
            tornadofx.launch<DebugUI>()
        }
    }
}