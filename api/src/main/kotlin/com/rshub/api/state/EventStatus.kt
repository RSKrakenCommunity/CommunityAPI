package com.rshub.api.state

class EventStatus {

    private var statusLevel: Int = 0
    val enforcement = mutableMapOf<ErrorStatus, Boolean>()

    val status: ErrorStatus get() {
        return when (statusLevel) {
            0 -> ErrorStatus.NONE
            in 1..5 -> ErrorStatus.LOW
            in 6..10 -> ErrorStatus.MEDIUM
            in 11 until Int.MAX_VALUE -> ErrorStatus.HIGH
            Int.MAX_VALUE -> ErrorStatus.DEATH
            else -> error("Status Level cannot be negative.")
        }
    }

    operator fun get(key: ErrorStatus, default: Boolean = false) : Boolean {
        return enforcement[key] ?: default
    }

    operator fun set(key: ErrorStatus, value: Boolean) {
        enforcement[key] = value
    }

    fun reset() {
        for (value in ErrorStatus.values()) {
            enforcement[value] = false
        }
        statusLevel = 0
    }

    fun flagStatus(death: Boolean = false) {
        if(death) {
            statusLevel = Int.MAX_VALUE
        } else {
            if((statusLevel + 1) >= Int.MAX_VALUE) {
                statusLevel = Int.MAX_VALUE
            } else {
                statusLevel++
            }
        }
    }

}