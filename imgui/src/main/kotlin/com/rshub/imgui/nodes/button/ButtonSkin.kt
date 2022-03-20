package com.rshub.imgui.nodes.button

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class ButtonSkin(override val node: Button) : Skin<Button> {
    override fun paint() {
        if(ImGui.button(node.text)) {
            node.action.invoke()
        }
    }
}