package com.rshub.api.entities.objects

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.ObjectAction
import com.rshub.api.definitions.CacheHelper
import com.rshub.api.entities.WorldEntity
import com.rshub.api.map.Region
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.state.SelfCorrectionState
import com.rshub.api.state.events.ObjectInteractError
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import com.rshub.definitions.objects.ObjectDefinition
import kraken.plugin.api.*
import kotlin.math.ceil

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

    fun interact(option: ObjectAction) : Boolean {
        val tile = globalPosition.toTile()
        val validTile = Region.validateObjCoords(id, globalPosition.toTile())
        val pos = globalPosition
        val valid = tile == validTile
        val x = (if (valid) pos.x else pos.x - ceil(def.sizeX.toDouble() / 2)).toInt()
        val y = (if (valid) pos.y else pos.y - ceil(def.sizeY.toDouble() / 2)).toInt()
        if (LocalPathing.isReachable(pos.toTile())) {
            val objId = if(interactId == -1) id else interactId
            ActionHelper.menu(option, objId, x, y)
            return true
        }
        SelfCorrectionState.events.tryEmit(ObjectInteractError(this))
        return false
    }

    fun interact(option: String): Boolean {
        for ((index, action) in def.options.withIndex()) {
            if(option.equals(action, true)) {
                return interact(ObjectAction.forAction(index))
            }
        }
        return false
    }

    inline operator fun <reified T> get(key: Int): T? {
        val map = def.params.map ?: return null
        if (!map.containsKey(key)) return null
        return map[key]!! as T
    }
}