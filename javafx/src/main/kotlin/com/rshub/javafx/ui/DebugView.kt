package com.rshub.javafx.ui

import com.rshub.api.kraken.BreakHelper
import com.rshub.javafx.ui.model.GlobalModel
import com.rshub.javafx.ui.tabs.PlayerTab
import com.rshub.javafx.ui.tabs.VariableDebuggerTab
import javafx.beans.binding.Bindings
import kraken.plugin.api.Kraken
import tornadofx.*
import java.util.concurrent.TimeUnit

class DebugView : View() {

    private val global: GlobalModel by inject()

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            baseColor = c("000000")
        }
        menubar {
            menu("File")
            menu("Breaks") {
                item("Cancel Break").setOnAction {
                    BreakHelper.cancel()
                }
                item("Take 5 Minute Break").setOnAction {
                    BreakHelper.takeBreak(5, TimeUnit.MINUTES)
                }
                item("Take 15 Minute Break").setOnAction {
                    BreakHelper.takeBreak(10, TimeUnit.MINUTES)
                }
                item("Take 30 Minute Break").setOnAction {
                    BreakHelper.takeBreak(20, TimeUnit.MINUTES)
                }
                item("Take 1 Hour Break").setOnAction {
                    BreakHelper.takeBreak(1, TimeUnit.HOURS)
                }
            }
        }
        tabpane {
            tab<PlayerTab> {
                closableProperty().bind(false.toProperty())
            }
            tab<VariableDebuggerTab> {
                closableProperty().bind(false.toProperty())
            }
        }
    }

}