package com.rshub.api.pathing.web.edges

interface EdgeStrategy {
    fun traverse(edge: Edge) : Boolean
    fun blocked(): Boolean
}