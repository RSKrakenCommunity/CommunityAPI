package com.rshub.api.input

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Kraken
import java.awt.Robot

/**
 * This class should be used only in bots that are run on a vm, the Robot class
 * intercepts the psychical devices.
 */

object RobotInputHelper {
    private val robot = Robot()

    @JvmStatic
    fun keyTyped(code: Int) {
        runBlocking {
            keyTyped(code, 0)
        }
    }

    @JvmStatic
    fun mouseScroll(scroll: Int) {
        runBlocking {
            mouseWheel(scroll)
        }
    }

    suspend fun mouseWheel(scroll: Int) {
        Kraken.focusGameWindow()
        delay(100)
        robot.mouseWheel(scroll)
    }

    suspend fun keyTyped(code: Int, delay: Long = 0L) {
        Kraken.focusGameWindow()
        delay(100)
        robot.keyPress(code)
        delay(delay)
        robot.keyRelease(code)
    }
}