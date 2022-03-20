package com.rshub.imgui.nodes.tabs

import com.rshub.imgui.skins.Skin
import kraken.plugin.api.ImGui

class TabPaneSkin(override val node: TabPane) : Skin<TabPane> {
    override fun paint() {
        if(ImGui.beginTabBar(node.text)) {
            for (tab in node.tabs) {
                if(ImGui.beginTabItem(tab.text)) {
                    tab.content.getSkin().paint()
                    ImGui.endTabItem()
                }
            }
            ImGui.endTabBar()
        }
    }
}