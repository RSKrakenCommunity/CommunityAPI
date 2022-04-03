package com.rshub.api.state

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kraken.plugin.api.Debug
import kraken.plugin.api.Kraken
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object SelfCorrectionState {

    val jobs = mutableListOf<Job>()
    val events = MutableSharedFlow<ErrorEvent<*>>(extraBufferCapacity = 100)
    val status = mutableMapOf<Class<*>, EventStatus>()

    fun start() {
        GlobalScope.launch(Dispatchers.SELF_CORRECTION) {
            for ((key, value) in status) {
                Debug.log("[${key.simpleName}]: Status now ${value.status.name}")
                if (!value[value.status, false]) {
                    when (value.status) {
                        ErrorStatus.NONE -> {}
                        ErrorStatus.LOW -> {}
                        ErrorStatus.MEDIUM -> {
                            Debug.log("[${key.simpleName}]: Enforcing 1 hour break.")
                            Kraken.takeBreak(TimeUnit.HOURS.toMillis(1L))
                        }
                        ErrorStatus.HIGH -> {
                            Debug.log("[${key.simpleName}]: Enforcing 8 hour break.")
                            Kraken.takeBreak(TimeUnit.HOURS.toMillis(8L))
                        }
                        ErrorStatus.DEATH -> {
                            Debug.log("[${key.simpleName}]: Disabling all plugins, system is dead.")
                            Kraken.getAllPlugins().forEach { it.isEnabled = false }
                            Kraken.toggleAutoLogin(false)
                        }
                    }
                    value[value.status] = true
                }
                delay(600)
            }
        }
    }

    fun reactToError(clazz: Class<*>, reaction: ErrorEvent<*>.(EventStatus) -> Unit): Job {
        return events
            .onEach {
                val status = status.getOrPut(clazz) { EventStatus() }
                status.flagStatus()
                reaction.invoke(it, status)
            }
            .launchIn(CoroutineScope(Dispatchers.SELF_CORRECTION))
            .apply { jobs.add(this) }
    }

    inline fun <reified R : ErrorEvent<R>> reactTo(crossinline reaction: R.(EventStatus) -> Unit): Job {
        return events.filterIsInstance<R>()
            .onEach {
                val status = status.getOrPut(R::class.java) { EventStatus() }
                status.flagStatus()
                reaction.invoke(it, status)
            }
            .launchIn(CoroutineScope(Dispatchers.SELF_CORRECTION))
            .apply { jobs.add(this) }
    }

    fun shutdown() {
        jobs.forEach(Job::cancel)
    }
    private val DISPATCHER = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val Dispatchers.SELF_CORRECTION get() = DISPATCHER
}