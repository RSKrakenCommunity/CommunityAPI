package com.rshub.imgui.nodes.pane.skins

import com.rshub.imgui.nodes.pane.panes.VerticalPane
import com.rshub.imgui.skins.Skin

class VerticalPaneSkin(override val node: VerticalPane) : Skin<VerticalPane> {
    override fun paint() {
        node.nodes.forEach { it.getSkin().paint() }
    }
}