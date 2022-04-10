package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.NpcAction
import com.rshub.definitions.maps.WorldTile
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class NpcStrategyEditorModel : ViewModel() {

    val npcId = bind { SimpleIntegerProperty(this, "npc_id", -1) }
    val locX = bind { SimpleIntegerProperty(this, "loc_x", -1) }
    val locY = bind { SimpleIntegerProperty(this, "loc_y", -1) }
    val locZ = bind { SimpleIntegerProperty(this, "loc_z", -1) }
    val action = bind { SimpleObjectProperty(this, "npc_action", NpcAction.NPC1) }

    fun clear() {
        npcId.set(-1)
        locX.set(-1)
        locY.set(-1)
        locZ.set(-1)
        action.set(NpcAction.NPC1)
    }

}