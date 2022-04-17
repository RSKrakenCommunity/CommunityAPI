package com.rshub.api.coroutines

import kraken.plugin.api.Rng

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

/**
 * for this to continue the predicate must be met, or the coroutine canceled.
 */

suspend fun delayIndefinitely(delay: Long = 600, predicate: suspend () -> Boolean) : Boolean {
    if(predicate()) return true
    while (!predicate()) {
        kotlinx.coroutines.delay(delay)
    }
    return true
}

suspend fun delay(value: Int) = kotlinx.coroutines.delay(value.toLong())
suspend fun delayRandom(min: Int, max: Int) = delay(Rng.i32(min, max))