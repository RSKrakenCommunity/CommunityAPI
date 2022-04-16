package com.rshub.api.gameframe.actionbar

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.widgets.WidgetHelper

enum class ActionSlot(private val buttonId: Int) {

    SLOT_1(64),
    SLOT_2(77),
    SLOT_3(90),
    SLOT_4(103),
    SLOT_5(116),
    SLOT_6(129),
    SLOT_7(142),
    SLOT_8(155),
    SLOT_9(168),
    SLOT_10(181),
    SLOT_11(194),
    SLOT_12(207),
    SLOT_13(220),
    SLOT_14(233);

    fun interact() {
        ActionHelper.menu(MenuAction.WIDGET, 1, -1, WidgetHelper.hash(ActionBar.ACTION_WIDGET_ID, buttonId))
    }

}