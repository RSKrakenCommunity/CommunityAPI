package com.rshub.api.interaction

import com.rshub.api.actions.ActionHelper
import com.rshub.api.actions.MenuAction
import com.rshub.api.definitions.DefinitionManager.Companion.def
import com.rshub.api.map.Region
import com.rshub.definitions.maps.MapObject
import kotlin.math.ceil

object ObjectInteraction {

    fun MapObject.interact(action: MenuAction) {
        val valid: Boolean = Region.validateObjCoords(this)
        val x = (if (valid) objectX else objectX - ceil(def.sizeX.toDouble() / 2)).toInt()
        val y = (if (valid) objectY else objectY - ceil(def.sizeY.toDouble() / 2)).toInt()
        ActionHelper.menu(action, objectId, x, y)
    }


}