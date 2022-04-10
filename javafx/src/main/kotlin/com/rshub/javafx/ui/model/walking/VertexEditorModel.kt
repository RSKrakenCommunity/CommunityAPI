package com.rshub.javafx.ui.model.walking

import com.rshub.api.pathing.web.edges.EdgeStrategy
import com.rshub.api.pathing.web.edges.strategies.DoorStrategy
import com.rshub.api.pathing.web.edges.strategies.EdgeTileStrategy
import com.rshub.api.pathing.web.edges.strategies.NpcStrategy
import com.rshub.api.pathing.web.edges.strategies.ObjectStrategy
import com.rshub.definitions.maps.WorldTile
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel

class VertexEditorModel : ViewModel() {

    private val osEditor: ObjectStrategyEditorModel by di()
    private val npcEditor: NpcStrategyEditorModel by di()
    private val doorEditor: DoorEditorModel by di()

    val usePlayerTile = bind { SimpleBooleanProperty(this, "use_player_tile", true) }
    val tileX = bind { SimpleIntegerProperty(this, "tile_x", -1) }
    val tileY = bind { SimpleIntegerProperty(this, "tile_y", -1) }
    val tileZ = bind { SimpleIntegerProperty(this, "tile_z", -1) }

    val strategy = bind { SimpleObjectProperty(this, "edge_strategy", EdgeStrategyType.TILE) }

    fun createStrategy(strategy: EdgeStrategyType = this.strategy.get()): EdgeStrategy {
        return when (strategy) {
            EdgeStrategyType.TILE -> EdgeTileStrategy()
            EdgeStrategyType.OBJECT -> {
                ObjectStrategy(
                    osEditor.objectId.get(),
                    osEditor.objectX.get(),
                    osEditor.objectY.get(),
                    osEditor.action.get(),
                    osEditor.skill.get(),
                    osEditor.level.get(),
                    timeoutMod = osEditor.timeout.get()
                ).also {
                    osEditor.clear()
                }
            }
            EdgeStrategyType.NPC -> {
                NpcStrategy(
                    npcEditor.npcId.get(),
                    WorldTile(npcEditor.locX.get(), npcEditor.locY.get(), 0),
                    npcEditor.action.get()
                ).also {
                    npcEditor.clear()
                }
            }
            EdgeStrategyType.DOOR -> {
                DoorStrategy(
                    doorEditor.openDoorId.get(),
                    doorEditor.closedDoorId.get(),
                    WorldTile(
                        doorEditor.openX.get(),
                        doorEditor.openY.get(),
                        doorEditor.openZ.get()
                    ),
                    WorldTile(
                        doorEditor.closedX.get(),
                        doorEditor.closedY.get(),
                        doorEditor.closedZ.get()
                    ),
                    doorEditor.action.get(),
                ).also {
                    doorEditor.clear()
                }
            }
        }
    }

}