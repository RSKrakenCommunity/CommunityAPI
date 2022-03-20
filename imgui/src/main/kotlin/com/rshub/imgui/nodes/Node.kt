package com.rshub.imgui.nodes

import com.rshub.imgui.skins.Skin

interface Node {

    fun getSkin(): Skin<*>

}