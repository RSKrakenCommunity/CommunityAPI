package com.rshub.imgui.nodes.label

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class LabelSkin(override val node: Label) : Skin<Label> {
    override fun paint() {
        ImGui.label(node.text)
    }
}