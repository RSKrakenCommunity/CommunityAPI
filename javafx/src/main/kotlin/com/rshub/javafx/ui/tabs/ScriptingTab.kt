package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.tabs.scripting.editor.util.editorPane
import tornadofx.Fragment
import tornadofx.anchorpane
import tornadofx.anchorpaneConstraints


class ScriptingTab : Fragment("Scripting") {
    override val root = anchorpane {
        editorPane {
            anchorpaneConstraints {
                topAnchor = 0.0
                bottomAnchor = 0.0
                leftAnchor = 0.0
                rightAnchor = 0.0
            }
        }
    }
}