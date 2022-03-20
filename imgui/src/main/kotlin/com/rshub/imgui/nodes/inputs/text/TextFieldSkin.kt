package com.rshub.imgui.nodes.inputs.text

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class TextFieldSkin(override val node: TextField) : Skin<TextField> {

    private val input = ByteArray(node.maxLength.get())

    override fun paint() {
        ImGui.input(node.text, input)
        node.input.set(String(input))
    }
}