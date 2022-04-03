package com.rshub.javafx.ui.model.walking

import com.rshub.api.actions.ObjectAction
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class DoorEditorModel : ViewModel() {

    val objectId = bind { SimpleIntegerProperty(this, "object_id") }
    val objectX = bind { SimpleIntegerProperty(this, "object_x") }
    val objectY = bind { SimpleIntegerProperty(this, "object_y") }
    val objectZ = bind { SimpleIntegerProperty(this, "object_z") }
    val action = bind { SimpleObjectProperty(this, "action", ObjectAction.OBJECT1) }

}