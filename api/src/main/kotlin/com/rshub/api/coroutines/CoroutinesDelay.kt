package com.rshub.api.coroutines

import kraken.plugin.api.Rng

object CoroutinesDelay {
    @JvmStatic
    suspend fun delayUntil(
        timeout: Long = 5000,
        delay: Long = 600,
        predicate: suspend () -> Boolean
    ): Boolean {
        if (predicate()) return true
        val begin = System.currentTimeMillis()
        while (System.currentTimeMillis() < (begin + timeout)) {
            kotlinx.coroutines.delay(delay)
            if (predicate()) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    suspend fun delay(value: Int) = kotlinx.coroutines.delay(value.toLong())
    @JvmStatic
    suspend fun delayRandom(min: Int, max: Int) = delay(Rng.i32(min, max))
}