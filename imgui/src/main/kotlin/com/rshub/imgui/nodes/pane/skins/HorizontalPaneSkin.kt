package com.rshub.imgui.nodes.pane.skins

import com.rshub.imgui.nodes.pane.panes.HorizontalPane
import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class HorizontalPaneSkin(override val node: HorizontalPane) : Skin<HorizontalPane> {
    override fun paint() {
        for (node in node.nodes) {
            node.getSkin().paint()
            ImGui.sameLine()
        }
    }
}