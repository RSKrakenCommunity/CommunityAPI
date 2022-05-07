package com.rshub.api.input

import kotlinx.coroutines.delay
import kraken.plugin.api.Input
import java.util.function.Consumer

object KrakenInputHelper {

    suspend fun typeKey(key: Int, delay: Long) {
        pressKey(key)
        delay(delay)
        releaseKey(key)
    }

    suspend fun typeKey(literal: Char, delay: Long) {
        typeKey(literal.code, delay)
    }

    @JvmStatic
    fun doKey(code: Int, consumer: Consumer<Void?>) {
        pressKey(code)
        consumer.accept(null)
        releaseKey(code)
    }

    @JvmStatic
    fun pressKey(code: Int) {
        Input.press(code)
    }

    @JvmStatic
    fun releaseKey(code: Int) {
        Input.release(code)
    }

    @JvmStatic
    fun typeKey(code: Int) {
        Input.key(code)
    }

    @JvmStatic
    fun typeCharLiteral(char: Char) {
        typeKey(char.code)
    }

    @JvmStatic
    fun moveMouse(x: Int, y: Int) {
        Input.moveMouse(x, y)
    }

    @JvmStatic
    fun clickMouse(button: Int) {
        Input.clickMouse(button)
    }

}