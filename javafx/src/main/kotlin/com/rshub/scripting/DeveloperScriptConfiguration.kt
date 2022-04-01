package com.rshub.scripting

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.pathing.walking.Traverse
import com.rshub.api.world.WorldHelper
import kraken.plugin.api.Client
import kraken.plugin.api.Debug
import kraken.plugin.api.Kraken
import kraken.plugin.api.Players
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object DeveloperScriptConfiguration : ScriptCompilationConfiguration({
    jvm {

        defaultImports(
            Debug::class,
            Client::class,
            Kraken::class,
            CacheHelper::class,
            ActionHelper::class,
            MenuAction::class,
            Players::class,
            WorldHelper::class,
            Traverse::class
        )

        dependenciesFromCurrentContext(wholeClasspath = true)
    }
})