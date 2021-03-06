package com.rshub.api.pathing.web.edges

interface EdgeStrategy {
    fun traverse(edge: Edge): Boolean
    fun reached(edge: Edge): Boolean
    fun modifyCost(cost: Int): Int
    fun blocked(edge: Edge): Boolean
    fun modifyTimeout(timeout: Long): Long {
        return timeout
    }
    fun skipThreshold() : Int {
        return 0
    }
}