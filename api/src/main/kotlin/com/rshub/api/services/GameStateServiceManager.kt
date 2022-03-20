package com.rshub.api.services

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class GameStateServiceManager {
    private val services = mutableSetOf<GameStateService>()

    fun registerService(service: GameStateService) {
        services.add(service)
    }

    fun start() = runBlocking(GAME_STATE_DISPATCHER) {
        services.forEach { it.stateChanged() }
    }

    fun run(runnable: suspend () -> Unit) {
        services.add(GameStateService { runnable() })
    }

    companion object {
        val GAME_STATE_EXECUTOR = Executors.newSingleThreadExecutor()
        val GAME_STATE_DISPATCHER = GAME_STATE_EXECUTOR.asCoroutineDispatcher()
    }
}