package com.rshub.imgui.nodes.inputs.text

import com.rshub.imgui.skins.Skin

class PasswordField(text: String) : TextField(text) {
    override fun getSkin(): Skin<*> {
        return PasswordFieldSkin(this)
    }
}