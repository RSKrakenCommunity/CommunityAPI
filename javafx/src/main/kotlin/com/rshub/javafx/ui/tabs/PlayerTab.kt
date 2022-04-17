package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.model.PlayerModel
import javafx.beans.binding.Bindings
import tornadofx.*

class PlayerTab : Fragment("Local Player") {

    private val playerModel: PlayerModel by di()

    override val root = hbox {
        form {
            spacing = 10.0
            paddingAll = 10.0
            fieldset("Player Statistics") {
                field {
                    label(playerModel.tile)
                }
                field {
                    label(playerModel.localTile)
                }
                field("Is Moving") {
                    label(playerModel.isMoving)
                }
                field("Animation ID") {
                    label(playerModel.animationId)
                }
                field("Server Index") {
                    label(playerModel.serverIndex)
                }
                field("Health") {
                    label(playerModel.health)
                }
                field("Adrenaline") {
                    label(playerModel.adrenaline)
                }
                field("Total Level") {
                    label(playerModel.totalLevel)
                }
            }
            fieldset("Interacting Statistics") {
                field("Interacting Index") {
                    label(playerModel.interactingIndex)
                }
                field("Interacting Spirit") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            spirit?.name ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
                field("Animation ID") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            spirit?.animationId?.toString() ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
                field("Moving") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            spirit?.isMoving?.toString() ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
            }
        }
        separator()
        form {
            fieldset("Spirit Interacting Statistics") {
                field("Interacting Index") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            val osp = spirit.interacting
                            osp?.interactingIndex?.toString() ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
                field("Interacting Spirit") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            val osp = spirit.interacting
                            osp?.name ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
                field("Animation ID") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            val osp = spirit.interacting
                            osp?.animationId?.toString() ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
                field("Moving") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.interacting.get()
                            val osp = spirit.interacting
                            osp?.isMoving?.toString() ?: "None"
                        }, playerModel.interactingIndex, playerModel.interacting))
                    }
                }
            }
        }
    }
}