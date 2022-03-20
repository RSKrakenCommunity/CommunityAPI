package com.rshub.imgui.nodes.inputs.integer

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class IntegerInputSkin(override val node: IntegerInput) : Skin<IntegerInput> {
    override fun paint() {
        node.input.set(ImGui.intInput(node.text, node.input.get()))
    }
}