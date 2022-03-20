package com.rshub.api.banking

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.containers.Container
import com.rshub.api.containers.Inventory
import com.rshub.api.input.InputHelper
import com.rshub.api.widgets.Widget
import com.rshub.api.widgets.WidgetHelper
import kraken.plugin.api.Bank
import kraken.plugin.api.WidgetItem

class Bank : Widget {
    override val widgetId: Int = 517
    private val closeButton = 302
    private val depositBurdenButton = 45

    private val containers = mutableMapOf<Int, Container>(
        95 to Inventory(95)
    )

    fun withdrawItem(id: Int, amount: Int = -1) {
        val option = when(amount) {
            1 -> WITHDRAW_ONE
            5 -> WITHDRAW_FIVE
            10 -> WITHDRAW_TEN
            -1 -> WITHDRAW_ALL
            else -> WITHDRAW_X
        }
        Bank.withdraw({ it.id == id }, option)
    }

    fun depositItem(id: Int, amount: Int = -1) {
        val option = when (amount) {
            1 -> DEPOSIT_ONE
            5 -> DEPOSIT_FIVE
            10 -> DEPOSIT_TEN
            -1 -> DEPOSIT_ALL
            else -> DEPOSIT_X
        }
        Bank.deposit({ it.id == id }, option)
    }

    fun depositInventory(useKey: Boolean = true) {
        if (useKey) {
            InputHelper.pressKey('3')
        } else {
            Bank.depositAll()
        }
    }

    fun depositEquipment(useKey: Boolean = true) {
        if (useKey) {
            InputHelper.pressKey('4')
        } else {
            Bank.depositEquipment()
        }
    }

    fun depositBurden(useKey: Boolean = true) {
        if (useKey) {
            InputHelper.pressKey('5')
        } else {
            ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, depositBurdenButton))
        }
    }

    override fun hasInventory(): Boolean {
        return containers.isNotEmpty()
    }

    override fun addContainer(con: Container) {
        containers[con.containerId] = con
    }

    override fun getContainer(id: Int): Container? {
        return containers[id]
    }

    override fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem) {

    }

    override fun exit() {
        InputHelper.pressKey(InputHelper.ESC)
    }

    override fun close() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, closeButton))
    }

    companion object {
        const val WITHDRAW_ONE = 1
        const val WITHDRAW_FIVE = 3
        const val WITHDRAW_TEN = 4
        const val WITHDRAW_X = 6
        const val WITHDRAW_ALL = 7

        const val DEPOSIT_ONE = 1
        const val DEPOSIT_FIVE = 3
        const val DEPOSIT_TEN = 4
        const val DEPOSIT_X = 6
        const val DEPOSIT_ALL = 7

        const val WEAR_ITEM = 9
    }
}