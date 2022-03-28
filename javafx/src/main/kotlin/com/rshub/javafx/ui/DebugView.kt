package com.rshub.javafx.ui

import com.rshub.javafx.ui.tabs.PlayerTab
import tornadofx.*

class DebugView : View() {

    override val root = anchorpane {
        style {
            baseColor = c("2e2e2e")
        }
        tabpane {
            anchorpaneConstraints {
                leftAnchor = 0.0
                rightAnchor = 0.0
                bottomAnchor = 0.0
                topAnchor = 0.0
            }
            tab<PlayerTab>()
        }
    }

}