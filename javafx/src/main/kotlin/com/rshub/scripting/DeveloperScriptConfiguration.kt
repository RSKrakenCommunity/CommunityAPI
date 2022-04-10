package com.rshub.scripting

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.actions.NpcAction
import com.rshub.api.actions.ObjectAction
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.entities.objects.WorldObject
import com.rshub.api.entities.spirits.npc.WorldNpc
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.walking.Traverse
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import kraken.plugin.api.*
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
            WorldNpc::class,
            WorldObject::class,
            NpcAction::class,
            ObjectAction::class,
            Traverse::class,
            LocalPathing::class,
            WorldTile::class,
            WorldTile.Companion::class,
            SceneObjects::class
        )

        dependenciesFromCurrentContext(wholeClasspath = true)
    }
})