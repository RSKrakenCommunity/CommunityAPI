package com.rshub.imgui.nodes.button

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleStringProperty

class Button(text: String) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", text)
    internal var action: () -> Unit = {}
        private set

    fun setOnAction(action: () -> Unit) {
        this.action = action
    }

    override fun getSkin(): Skin<Button> {
        return ButtonSkin(this)
    }
}