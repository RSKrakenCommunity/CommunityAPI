package com.rshub.api.gameframe.actionbar

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.variables.Variables
import com.rshub.api.widgets.WidgetHelper
import kotlinx.coroutines.runBlocking

object ActionBar {
    const val ACTION_WIDGET_ID = 1430
    const val NEXT_BAR_BUTTON_ID = 259
    const val PREV_BAR_BUTTON_ID = 258
    const val LOCK_BAR_BUTTON_ID = 262

    val barNumber by Variables.ACTION_BAR_NUMBER
    private val isLocked by Variables.ACTION_BAR_LOCKED

    @JvmStatic
    fun isLocked() = isLocked == 1

    @JvmStatic
    fun interact(slot: ActionSlot) {
        slot.interact()
    }

    @JvmStatic
    fun nextBar() {
        runBlocking { next() }
    }

    @JvmStatic
    fun prevBar() {
        runBlocking { previous() }
    }

    @JvmStatic
    fun lockBar() {
        runBlocking { lock() }
    }

    @JvmStatic
    fun unlockBar() {
        runBlocking { unlock() }
    }

    suspend fun lock() : Boolean {
        if(!isLocked()) {
            ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(ACTION_WIDGET_ID, LOCK_BAR_BUTTON_ID))
            return delayUntil { isLocked() }
        }
        return true
    }

    suspend fun unlock() : Boolean {
        if(isLocked()) {
            ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(ACTION_WIDGET_ID, LOCK_BAR_BUTTON_ID))
            return delayUntil { !isLocked() }
        }
        return false
    }

    suspend fun next() : Boolean {
        val current = barNumber
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(ACTION_WIDGET_ID, NEXT_BAR_BUTTON_ID))
        return delayUntil { barNumber == (current + 1) }
    }

    suspend fun previous() : Boolean {
        val current = barNumber
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(ACTION_WIDGET_ID, PREV_BAR_BUTTON_ID))
        return delayUntil { barNumber == (current - 1) }
    }
}