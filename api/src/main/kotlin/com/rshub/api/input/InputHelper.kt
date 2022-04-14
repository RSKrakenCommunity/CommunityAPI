package com.rshub.api.input

import kraken.plugin.api.Input

object InputHelper {
    const val ESC = 27
    const val ALT = 18
    const val ENTER = 13
    const val SPACE = 32
    const val F1 = 112
    const val F2 = 113
    const val F3 = 114
    const val F4 = 115
    const val F5 = 116
    const val F6 = 117
    const val F7 = 118
    const val F8 = 119
    const val F9 = 120
    const val F10 = 121
    const val F11 = 122
    const val F12 = 123

    @JvmStatic
    fun clickMouse(x: Int, y: Int, button: Int) {
        Input.moveMouse(x, y)
        Input.clickMouse(button)
    }

    @JvmStatic
    fun pressKey(c: Char) {
        Input.key(c.code)
    }

    @JvmStatic
    fun pressKey(key: Int) {
        Input.key(key)
    }
}