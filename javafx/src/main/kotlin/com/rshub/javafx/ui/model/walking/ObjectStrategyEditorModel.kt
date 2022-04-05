package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.ObjectAction
import com.rshub.api.skills.Skill
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class ObjectStrategyEditorModel : ViewModel() {

    val objectId = bind { SimpleIntegerProperty(this, "object_id", -1) }
    val objectX = bind { SimpleIntegerProperty(this, "object_x", -1) }
    val objectY = bind { SimpleIntegerProperty(this, "object_y", -1) }
    val objectZ = bind { SimpleIntegerProperty(this, "object_z", -1) }
    val action = bind { SimpleObjectProperty(this, "action", ObjectAction.OBJECT1) }

    val skill = bind { SimpleObjectProperty(this, "skill", Skill.NONE) }
    val level = bind { SimpleIntegerProperty(this, "level", 0) }

    val timeout = bind { SimpleLongProperty(this, "timeout", 0) }

    fun clear() {
        objectId.set(-1)
        objectX.set(-1)
        objectY.set(-1)
        objectZ.set(-1)
        action.set(ObjectAction.OBJECT1)
        skill.set(Skill.NONE)
        level.set(0)
        timeout.set(0)
    }

}