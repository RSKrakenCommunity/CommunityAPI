package com.rshub.api.pathing.web.edges

import com.rshub.api.pathing.web.nodes.GraphVertex
import com.rshub.api.variables.Variable
import kotlinx.serialization.Serializable

@Serializable
class Edge(
    val from: GraphVertex,
    val to: GraphVertex,
    val strategy: EdgeStrategy,
    val variableConditions: List<Pair<Variable, Int>> = emptyList()
) {
    fun traverse() = strategy.traverse(this)
    fun blocked() : Boolean {
        for (condition in variableConditions) {
            val (variable, expected) = condition
            if(variable.value != expected) {
                return true
            }
        }
        return strategy.blocked(this)
    }
    fun reached() = strategy.reached(this)
}

