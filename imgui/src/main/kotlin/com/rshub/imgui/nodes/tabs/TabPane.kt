package com.rshub.imgui.nodes.tabs

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.nodes.Node
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

class TabPane(title: String) : Labeled {

    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", title)

    val tabs = SimpleListProperty<Tab>(this, "tabs", FXCollections.observableArrayList())

    fun addTab(tab: Tab) {
        tabs.add(tab)
    }

    override fun getSkin(): Skin<*> {
        return TabPaneSkin(this)
    }
}