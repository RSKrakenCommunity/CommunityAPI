package com.rshub.imgui.skins

import com.rshub.imgui.nodes.Node

interface Skin<T : Node> {
    val node: T
    fun paint()
}