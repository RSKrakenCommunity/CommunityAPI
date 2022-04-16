package com.rshub.api.gameframe

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.input.KrakenInputHelper
import com.rshub.api.input.RobotInputHelper
import kraken.plugin.api.Client
import kraken.plugin.api.GameTheme

object GameFrame {

    @JvmStatic
    val MODE: GameTheme get() = Client.getInterfaceMode()

    @JvmStatic
    fun openSkillsTab() {
        when(MODE) {
            GameTheme.NEW -> KrakenInputHelper.typeCharLiteral('H')
            GameTheme.LEGACY -> ActionHelper.menu(MenuAction.WIDGET, 1, 8, 93782016)
        }
    }

    @JvmStatic
    fun openEquipmentTab() {
        when(MODE) {
            GameTheme.NEW -> KrakenInputHelper.typeCharLiteral('N')
            GameTheme.LEGACY -> ActionHelper.menu(MenuAction.WIDGET, 1, 10, 93782016)
        }
    }

}