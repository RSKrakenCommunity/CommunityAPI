package com.rshub.imgui.nodes.inputs.text

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class PasswordFieldSkin(override val node: TextField) : Skin<TextField> {
    private val input = ByteArray(node.maxLength.get())
    override fun paint() {
        ImGui.inputPassword(node.text, input)
        node.input.set(String(input))
    }
}