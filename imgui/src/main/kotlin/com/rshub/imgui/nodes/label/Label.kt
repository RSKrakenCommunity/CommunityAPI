package com.rshub.imgui.nodes.label

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleStringProperty

class Label(text: String) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", text)
    override fun getSkin(): Skin<Label> {
        return LabelSkin(this)
    }
}