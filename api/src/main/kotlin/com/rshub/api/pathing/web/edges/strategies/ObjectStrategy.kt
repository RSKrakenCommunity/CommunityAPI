package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.skills.Skill
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile.Companion.toTile
import kotlinx.serialization.Serializable
import kraken.plugin.api.Debug
import kraken.plugin.api.Players

@Serializable
class ObjectStrategy(
    val objectId: Int,
    val objectX: Int,
    val objectY: Int,
    val option: ObjectAction,
    val skill: Skill = Skill.NONE,
    val level: Int = 0,
    val timeoutMod: Long = 0
) : EdgeStrategy {

    override fun traverse(edge: Edge): Boolean {
        Debug.log("Object($objectId, $objectX, $objectY, $option) - ${edge.from.tile}")
        val obj = WorldHelper.closestObjectIgnoreClip {
            val pos = it.globalPosition
            val plr = Players.self()
            it.id == objectId && pos.x == objectX && pos.y == objectY && pos.z == plr.globalPosition.z
        }
        return obj?.interact(option, false) ?: false
    }

    override fun blocked(edge: Edge): Boolean {
        if(skill !== Skill.NONE) {
            if(skill.level <= 0) return false
            return skill.level < level
        }
        return false
    }

    override fun reached(edge: Edge): Boolean {
        val player = Players.self()
        if(player != null) {
            return player.globalPosition.toTile() == edge.to.tile && !player.isMoving
        }
        return false
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }

    override fun modifyTimeout(timeout: Long): Long {
        return timeout + this.timeoutMod
    }

    override fun skipThreshold(): Int {
        return 1
    }
}