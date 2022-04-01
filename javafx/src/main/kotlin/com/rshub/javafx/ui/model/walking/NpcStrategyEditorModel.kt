package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.NpcAction
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class NpcStrategyEditorModel : ViewModel() {

    val npcId = bind { SimpleIntegerProperty(this, "npc_id", -1) }
    val action = bind { SimpleObjectProperty<NpcAction>(this, "npc_action", NpcAction.NPC1) }

}