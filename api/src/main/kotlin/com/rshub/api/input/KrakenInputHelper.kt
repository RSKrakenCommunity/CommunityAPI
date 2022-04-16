package com.rshub.api.input

import kraken.plugin.api.Input

object KrakenInputHelper {

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