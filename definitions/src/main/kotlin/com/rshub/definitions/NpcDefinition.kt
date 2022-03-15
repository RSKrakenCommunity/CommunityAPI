package com.rshub.definitions

import com.rshub.utilities.isJustNulls
import kotlinx.serialization.json.*

class NpcDefinition(override val id: Int) : Definition {

    var params = Params()
    var modelIds: IntArray = intArrayOf()
    var name = ""
    var size = 1
    var options = arrayOfNulls<String>(5)
    var originalColors: ShortArray = shortArrayOf()
    var modifiedColors: ShortArray = shortArrayOf()
    var originalTextures: ShortArray = shortArrayOf()
    var modifiedTextures: ShortArray = shortArrayOf()
    var recolourPalette: ByteArray = byteArrayOf()
    var headModels: IntArray = intArrayOf()
    var drawMapdot = false
    var combatLevel = 0
    var resizeX = 0
    var resizeY = 0
    var aBool4904 = false
    var ambient: Byte = 0
    var contrast: Byte = 0
    var headIcons: Map<Int, Int>? = null
    var rotation = 0
    var varbit = -1
    var varp = -1
    var transformTo: IntArray = intArrayOf()
    var visible = true
    var isClickable = true
    var animateIdle = true
    var aShort4874 = 0
    var aShort4897 = 0
    var aByte4883: Byte = 0
    var aByte4899: Byte = 0
    var walkMask: Byte = 0
    var modelTranslation: Array<IntArray?> = Array(0) { null }
    var height = 0
    var respawnDirection: Byte = 0
    var basId = 0
    var movementType: MovementType? = null
    var walkingAnimation = 0
    var rotate180Animation = 0
    var rotate90RightAnimation = 0
    var rotate90LeftAnimation = 0
    var specialByte = 0
    var anInt4875 = 0
    var anInt4873 = 0
    var anInt4854 = 0
    var anInt4861 = 0
    var attackOpCursor = 0
    var armyIcon = 0
    var anInt4909 = 0
    var aBool4884 = false
    var mapIcon = 0
    var aBool4890 = false
    var membersOptions = arrayOfNulls<String>(5)
    var aByte4868: Byte = 0
    var aByte4869: Byte = 0
    var aByte4905: Byte = 0
    var aByte4871: Byte = 0
    var aByte4916 = 0
    var quests: IntArray = intArrayOf()
    var aBool4872 = false
    var anInt4917 = 0
    var anInt4911 = 0
    var anInt4919 = 0
    var anInt4913 = 0
    var anInt4908 = 0
    var aBool4920 = true
    var actionCursors: IntArray? = null

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("npcId", id)
            put("name", name)
            put("is_clickable", isClickable)
            put("size", size)
            put("visible", visible)
            put("vars", buildJsonObject {
                put("varp", varp)
                put("varbit", varbit)
                if (transformTo.isNotEmpty()) {
                    put("transforms", buildJsonArray {
                        transformTo.forEach {
                            add(it)
                        }
                    })
                }
            })
            val actionsObj = buildJsonObject {
                if (!options.isJustNulls()) {
                    put("options", buildJsonArray {
                        options.forEach {
                            add(it ?: "null")
                        }
                    })
                }
                if (!membersOptions.isJustNulls()) {
                    put("members_options", buildJsonArray {
                        membersOptions.forEach {
                            add(it ?: "null")
                        }
                    })
                }
            }
            if(actionsObj.isNotEmpty()) {
                put("actions", actionsObj)
            }
            val p = params.toJsonObject()
            if(p.isNotEmpty()) {
                put("params", p)
            }
        }
    }

    enum class MovementType(var id: Int) {
        STATIONARY(-1), HALF_WALK(0), WALKING(1), RUNNING(2);

        companion object {
            fun forId(id: Int): MovementType? {
                for (type in values()) {
                    if (type.id == id) return type
                }
                return null
            }
        }
    }
}