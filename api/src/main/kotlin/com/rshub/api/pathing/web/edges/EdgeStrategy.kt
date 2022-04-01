package com.rshub.api.pathing.web.edges

import com.rshub.api.pathing.json.EdgeStrategySerializer
import kotlinx.serialization.Serializable

@Serializable(with = EdgeStrategySerializer::class)
interface EdgeStrategy {
    fun traverse(edge: Edge) : Boolean
    fun reached(edge: Edge): Boolean
    fun isPlayerMoving() : Boolean
    fun modifyCost(cost: Int) : Int
    fun blocked(edge: Edge): Boolean
}