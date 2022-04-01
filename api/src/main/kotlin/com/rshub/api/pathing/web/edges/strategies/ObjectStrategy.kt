package com.rshub.api.pathing.web.edges.strategies

import com.rshub.api.actions.ObjectAction
import com.rshub.api.pathing.LocalPathing
import com.rshub.api.pathing.web.edges.Edge
import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.skills.Skill
import com.rshub.api.world.WorldHelper
import com.rshub.definitions.maps.WorldTile
import com.rshub.definitions.maps.WorldTile.Companion.expand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kraken.plugin.api.Players

@Serializable
class ObjectStrategy(
    val objectId: Int,
    val objectX: Int,
    val objectY: Int,
    val objectZ: Int,
    @Contextual val option: ObjectAction,
    @Contextual val skill: Skill = Skill.NONE,
    val level: Int = 0
) : EdgeStrategy {

    override fun traverse(edge: Edge): Boolean {
        val obj =
            WorldHelper.closestObject {
                val tile = it.getTile()
                it.id == objectId
                        && tile.x == objectX
                        && tile.y == objectY
                        && tile.z == objectZ
            }
        return obj?.interact(option) ?: false
    }

    override fun blocked(edge: Edge): Boolean {
        if(skill !== Skill.NONE) {
            return skill.level < level && LocalPathing.isReachable(edge.from.tile)
        }
        return LocalPathing.isReachable(edge.from.tile)
    }

    override fun reached(edge: Edge): Boolean {
        val tile = WorldTile(objectX, objectY, objectZ)
        val player = Players.self() ?: return false
        return tile.expand(2).contains(player)
    }

    override fun modifyCost(cost: Int): Int {
        return cost
    }
}