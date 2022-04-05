package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.ObjectAction
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class DoorEditorModel : ViewModel() {

    val objectId = bind { SimpleIntegerProperty(this, "object_id", -2) }
    val objectX = bind { SimpleIntegerProperty(this, "object_x", -2) }
    val objectY = bind { SimpleIntegerProperty(this, "object_y", -2) }
    val objectZ = bind { SimpleIntegerProperty(this, "object_z", -2) }
    val action = bind { SimpleObjectProperty(this, "action", ObjectAction.OBJECT1) }

    fun clear() {
        objectId.set(-1)
        objectX.set(-1)
        objectY.set(-1)
        objectZ.set(-1)
        action.set(ObjectAction.OBJECT1)
    }

}