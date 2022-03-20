package com.rshub.api.services

fun interface GameStateService {
    suspend fun stateChanged()
}