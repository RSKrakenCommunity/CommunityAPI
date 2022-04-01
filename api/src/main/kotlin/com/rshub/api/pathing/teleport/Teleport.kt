package com.rshub.api.pathing.teleport

interface Teleport {

    suspend fun teleport(): Boolean
    suspend fun isAvailable(): Boolean

}