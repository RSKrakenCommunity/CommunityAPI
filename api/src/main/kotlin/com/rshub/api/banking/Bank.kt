package com.rshub.api.banking

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.containers.Container
import com.rshub.api.containers.Inventory
import com.rshub.api.coroutines.delayRandom
import com.rshub.api.coroutines.delayUntil
import com.rshub.api.entities.items.ContainerItem.Companion.toContainerItem
import com.rshub.api.entities.items.GameItem
import com.rshub.api.input.InputHelper
import com.rshub.api.services.GameStateServiceManager.GAME_STATE
import com.rshub.api.variables.Variable
import com.rshub.api.variables.VariableHelper
import com.rshub.api.variables.impl.VariableBit
import com.rshub.api.widgets.Widget
import com.rshub.api.widgets.WidgetEvent
import com.rshub.api.widgets.WidgetHelper
import com.rshub.api.widgets.events.ContainerChangedEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kraken.plugin.api.Bank
import kraken.plugin.api.WidgetItem

class Bank : Widget {
    override val widgetId: Int = 517
    private val closeButton = 302
    private val depositBurdenButton = 45
    private val placeHolderButton = 35

    val invTab by Variable
    val bankTab by Variable
    val selectedPreset by Variable
    val transferX by Variable
    val transferAmount by Variable

    private val presetsOpen by Variable
    val isPresetsOpen get() = presetsOpen == 1
    private val presetSumInv by Variable
    val isSumInv get() = presetSumInv == 1
    private val presetInv by Variable
    val isPresetInv get() = presetInv == 1
    private val presetEquip by Variable
    val isPresetEquipment get() = presetEquip == 1
    private val isPresets by Variable
    val isPresetTab get() = isPresets == 1
    private val usePlaceholders by Variable
    val isPlaceholdersEnabled get() = usePlaceholders == 1

    private val containers = mutableMapOf<Int, Container>(
        95 to Inventory(95)
    )

    override val events: MutableSharedFlow<WidgetEvent<*>> = MutableSharedFlow(extraBufferCapacity = 100)

    override fun watchContainers(action: (Widget, Container, GameItem, GameItem) -> Unit) {
        events.filterIsInstance<ContainerChangedEvent>()
            .onEach {
                action(it.source, it.con, it.prev, it.next)
            }.launchIn(CoroutineScope(Dispatchers.GAME_STATE))
    }

    fun togglePlaceholders() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, placeHolderButton))
    }

    fun toggleNotes() {
        Bank.setWithdrawingNotes(!Bank.isWithdrawingNotes())
    }

    fun withdrawItem(id: Int, amount: Int = -1) {
        val option = when (amount) {
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

    fun depositALL(useKey: Boolean = true) = runBlocking { depositEverything(useKey) }

    suspend fun depositEverything(useKey: Boolean = true) {
        depositInventory(useKey)
        delayRandom(800, 1200)
        depositEquipment(useKey)
        delayRandom(800, 1200)
        depositBurden(useKey)
        delayRandom(800, 1200)
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

    override suspend fun containerChanged(container: Container, prev: WidgetItem, next: WidgetItem) {
        events.emit(
            ContainerChangedEvent(
                this,
                container,
                prev.toContainerItem(container),
                next.toContainerItem(container)
            )
        )
    }

    override fun exit() {
        InputHelper.pressKey(InputHelper.ESC)
    }

    fun closeBank() = runBlocking { close() }

    override suspend fun close() {
        exit()
        if (!delayUntil { isClosed() }) {
            ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(widgetId, closeButton))
        }
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

        init {
            VariableHelper.registerVariable("bank", "invTab", VariableBit(45319))
            VariableHelper.registerVariable("bank", "bankTab", VariableBit(45141))
            VariableHelper.registerVariable("bank", "presetsOpen", VariableBit(39433))
            VariableHelper.registerVariable("bank", "selectedPreset", VariableBit(22179))
            VariableHelper.registerVariable("bank", "presetSumInv", VariableBit(26177))
            VariableHelper.registerVariable("bank", "presetInv", VariableBit(22158))
            VariableHelper.registerVariable("bank", "presetEquip", VariableBit(22159))
            VariableHelper.registerVariable("bank", "isPresets", VariableBit(45191))
            VariableHelper.registerVariable("bank", "transferX", VariableBit(2567))
            VariableHelper.registerVariable("bank", "transferAmount", VariableBit(45189))
            VariableHelper.registerVariable("bank", "usePlaceholders", VariableBit(45190))
        }
    }
}