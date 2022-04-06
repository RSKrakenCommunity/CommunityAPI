package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.ObjectAction
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class DoorEditorModel : ViewModel() {

    val openDoorId = bind { SimpleIntegerProperty(this, "open_door_id", -1) }
    val closedDoorId = bind { SimpleIntegerProperty(this, "closed_door_id", -1) }
    val openX = bind { SimpleIntegerProperty(this, "open_x", -1) }
    val openY = bind { SimpleIntegerProperty(this, "open_y", -1) }
    val openZ = bind { SimpleIntegerProperty(this, "open_z", -1) }

    val closedX = bind { SimpleIntegerProperty(this, "closed_x", -1) }
    val closedY = bind { SimpleIntegerProperty(this, "closed_y", -1) }
    val closedZ = bind { SimpleIntegerProperty(this, "closed_z", -1) }

    val action = bind { SimpleObjectProperty(this, "action", ObjectAction.OBJECT1) }

    fun clear() {
        openDoorId.set(-1)
        closedDoorId.set(-1)
        openX.set(-1)
        openY.set(-1)
        openZ.set(-1)
        closedX.set(-1)
        closedY.set(-1)
        closedZ.set(-1)
        action.set(ObjectAction.OBJECT1)
    }

}