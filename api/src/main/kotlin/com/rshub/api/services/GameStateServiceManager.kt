package com.rshub.api.services

import kotlinx.coroutines.*
import java.util.concurrent.Executors

object GameStateServiceManager {
    private val services = mutableSetOf<GameStateService>()
    private var job: Job? = null

    fun registerService(service: GameStateService) {
        services.add(service)
    }

    fun start() {
        job = GlobalScope.launch(GAME_STATE_DISPATCHER) {
            while(isActive) {
                services.forEach { it.stateChanged() }
                delay(600)
            }
        }
    }

    fun run(runnable: suspend () -> Unit) {
        services.add(GameStateService { runnable() })
    }

    fun stop() {
        job?.cancel()
    }

    private val GAME_STATE_EXECUTOR = Executors.newSingleThreadExecutor()
    private val GAME_STATE_DISPATCHER = GAME_STATE_EXECUTOR.asCoroutineDispatcher()
    val Dispatchers.GAME_STATE get() = GAME_STATE_DISPATCHER
}