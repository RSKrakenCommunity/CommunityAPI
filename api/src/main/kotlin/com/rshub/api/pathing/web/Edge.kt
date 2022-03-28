package com.rshub.api.pathing.web

interface Edge {

    val from: Int
    val to: Int

    fun blocked(): Boolean

}