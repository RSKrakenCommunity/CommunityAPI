package com.rshub.api.widgets.trade

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.containers.Container
import com.rshub.api.coroutines.delayRandom
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.entities.items.GameItem
import com.rshub.api.input.KrakenInputHelper
import com.rshub.api.widgets.Widget
import com.rshub.api.widgets.WidgetEvent
import com.rshub.api.widgets.WidgetHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.WidgetItem
import java.awt.event.KeyEvent

/**
 * This interface appears to be if1... containers remain empty
 */
object TradeWidget : Widget {
    override val widgetId: Int = 335
    private val closeButtonId = 6
    private val acceptButtonId = 48
    private val declineButtonId = 50
    private val offerAllButtonId = 27
    private val offerCoinButtonId = 29

    override fun hasInventory(): Boolean {
        return false
    }

    override fun addContainer(con: Container) {}

    override fun getContainer(id: Int): Container? {
        return null
    }

    override suspend fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem) {}

    override val events: MutableSharedFlow<WidgetEvent<*>> = MutableSharedFlow(extraBufferCapacity = 100)

    override fun watchContainers(action: (Widget, Container, GameItem, GameItem) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun exit() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, closeButtonId))
    }

    override suspend fun close() {
        if (isOpen()) {
            KrakenInputHelper.typeKey(KeyEvent.VK_ESCAPE)
        }
        if (!delayUntil { isClosed() }) {
            exit()
        }
    }

    fun offerAll() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, offerAllButtonId))
    }

    fun accept() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, acceptButtonId))
    }

    fun decline() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, declineButtonId))
    }

    fun offerCoin(amount: Int) {
        runBlocking { offerCoins(amount) }
    }

    suspend fun offerCoins(amount: Int) {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, offerCoinButtonId))
        delayRandom(850, 1250)
        KrakenInputHelper.typeCharacters(amount.toString())
    }

    /**
     * Seems to use if1 containers...
     */

    object Confirmation : Widget {
        override val widgetId: Int = 334
        private val closeButtonId = 6

        override fun hasInventory(): Boolean {
            return false
        }

        override fun addContainer(con: Container) {}

        override fun getContainer(id: Int): Container? {
            return null
        }

        override suspend fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem) {}

        override val events: MutableSharedFlow<WidgetEvent<*>> = MutableSharedFlow(extraBufferCapacity = 100)

        override fun watchContainers(action: (Widget, Container, GameItem, GameItem) -> Unit) {}

        override fun exit() {
            ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, closeButtonId))
        }

        override suspend fun close() {
            if (isOpen()) {
                KrakenInputHelper.typeKey(KeyEvent.VK_ESCAPE)
            }
            if (!delayUntil { isClosed() }) {
                exit()
            }
        }

    }
}