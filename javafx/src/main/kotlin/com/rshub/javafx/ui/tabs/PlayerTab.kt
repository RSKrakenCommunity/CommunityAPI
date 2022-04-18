package com.rshub.javafx.ui.tabs

import com.rshub.javafx.ui.model.PlayerModel
import javafx.beans.binding.Bindings
import kraken.plugin.api.Npcs
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

                field("In Combat") {
                    label(playerModel.inCombat)
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
            fieldset("Attacking Spirit Statistics") {
                field("Interacting Index") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.interactingIndex?.toString() ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
                field("Interacting Spirit") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.name ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
                field("Animation ID") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.animationId?.toString() ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
                field("Moving") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.isMoving?.toString() ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
                field("Tagged") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.isTagged?.toString() ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
                field("Direction Offset") {
                    label {
                        textProperty().bind(Bindings.createStringBinding({
                            val spirit = playerModel.attackingSpirit.get()
                            val osp = Npcs.byServerIndex(spirit)
                            osp?.directionOffset?.toString() ?: "None"
                        }, playerModel.attackingSpirit))
                    }
                }
            }
        }
    }
}