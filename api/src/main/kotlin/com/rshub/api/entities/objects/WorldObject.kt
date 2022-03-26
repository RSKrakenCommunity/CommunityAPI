package com.rshub.api.entities.objects

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.entities.WorldEntity
import com.rshub.definitions.objects.ObjectDefinition
import kraken.plugin.api.*

class WorldObject(private val so: SceneObject) : WorldEntity {

    val id get() = so.id
    val size: Vector2i get() = so.size
    val interactId get() = getTransformedObject()

    override val globalPosition: Vector3i get() = so.globalPosition
    override val scenePosition: Vector3 get() = so.scenePosition

    val def: ObjectDefinition by lazy { CacheHelper.getObject(id) }

    val name get() = def.name
    val hidden get() = def.hidden
    val options: Array<String?> get() = def.options
    val isMembers: Boolean get() = def.members

    val varp: ConVar? get() = Client.getConVarById(def.varp)
    val varbit: Int get() = CacheHelper.getVarbitValue(def.varpBit)

    private fun getTransformedObject(): Int {
        if (def.transformTo.isEmpty()) return this.id
        val index = varbit
        if (index < 0 || index >= def.transformTo.size) return this.id
        return def.transformTo[index]
    }

    inline operator fun <reified T> get(key: Int): T? {
        val map = def.params.map ?: return null
        if (!map.containsKey(key)) return null
        return map[key]!! as T
    }
}