package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.tabs.variables.VariableDebuggerTab
import com.rshub.javafx.ui.tabs.variables.VariableEditorTab
import tornadofx.Fragment
import tornadofx.tabpane
import tornadofx.toProperty

class VariableTabView : Fragment("Varbits/Varps") {
    override val root = tabpane {
        tab<VariableDebuggerTab> {
            closableProperty().bind(false.toProperty())
        }
        tab<VariableEditorTab> {
            closableProperty().bind(false.toProperty())
        }
    }
}