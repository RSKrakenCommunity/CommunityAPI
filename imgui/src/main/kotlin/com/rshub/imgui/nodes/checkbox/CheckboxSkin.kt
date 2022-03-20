package com.rshub.imgui.nodes.checkbox

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class CheckboxSkin(override val node: Checkbox) : Skin<Checkbox> {
    override fun paint() {
        node.input.set(ImGui.checkbox(node.text, node.input.get()))
    }
}