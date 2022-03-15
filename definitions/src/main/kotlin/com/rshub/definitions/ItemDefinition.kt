package com.rshub.definitions

import com.rshub.utilities.isJustNulls
import kotlinx.serialization.json.*

class ItemDefinition(override val id: Int) : Definition {

    var modelId = 0
    var name = ""
    var modelZoom = 2000
    var modelRotationX = 0
    var modelRotationY = 0
    var modelOffsetX = 0
    var modelOffsetY = 0
    var stackable: Byte = 0
    var price = 1
    var equipmentSlot: Byte = -1
    var equipmentType: Byte = -1
    var op15Bool = false
    var members = false
    var unused = 0
    var maleModel1 = -1
    var maleModel2 = -1
    var femaleModel1 = -1
    var femaleModel2 = -1
    var equipmentType2: Byte = -1
    var wornActions = arrayOfNulls<String>(7)
    var groundActions = arrayOfNulls<String>(5)
    var inventoryActions = arrayOfNulls<String>(5)
    var originalColors: ShortArray = shortArrayOf()
    var replacementColors: ShortArray = shortArrayOf()
    var originalTextures: ShortArray = shortArrayOf()
    var replacementTextures: ShortArray = shortArrayOf()
    var recolorPalette: ByteArray = byteArrayOf()
    var stockMarket = false
    var maleModel3 = -1
    var femaleModel3 = -1
    var maleHeadModel1 = -1
    var femaleHeadModel1 = -1
    var maleHeadModel2 = -1
    var femaleHeadModel2 = -1
    var modelAngleZ = 0
    var searchable: Byte = 0
    var notedItemId = -1
    var notedTemplate = -1
    var stackAmounts: IntArray? = null
    var stackIds: IntArray? = null
    var resizeX = 0
    var resizeY = 0
    var resizeZ = 0
    var ambient: Byte = 0
    var contrast = 0
    var teamId: Byte = 0
    var lentItemId = -1
    var lendTemplate = -1
    var maleModelOffsetX = 0
    var maleModelOffsetY = 0
    var maleModelOffsetZ = 0
    var femaleModelOffsetX = 0
    var femaleModelOffsetY = 0
    var femaleModelOffsetZ = 0
    var questIds: IntArray = intArrayOf()
    var pickSizeShift = 0
    var bindId = -1
    var boundTemplate = -1
    var groundCursors: IntArray? = null
    var inventoryCursors: IntArray? = null
    var shardItemId = -1
    var shardTemplateId = -1
    var params = Params()
    var buffEffect: String? = null
    var geBuyLimit = 0
    var category: Short = 0
    var randomizeGroundPos = false
    var shardCombineAmount: Short = 0
    var shardName: String? = null
    var neverStackable = false
    var noted = false
    var lended = false

    fun loadEquippedOps() {
        wornActions[0] = params.getString(528)
        wornActions[1] = params.getString(529)
        wornActions[2] = params.getString(530)
        wornActions[3] = params.getString(531)
        wornActions[4] = params.getString(1211)
        wornActions[5] = params.getString(6712)
        wornActions[6] = params.getString(6713)
    }

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("itemId", id)
            put("name", name)
            put("buff_effect", buffEffect)
            put("category", category)
            if(shardItemId != -1) {
                put("item_shard", buildJsonObject {
                    put("shardId", shardItemId)
                    put("shard_name", shardName)
                    put("shard_combine_amount", shardCombineAmount)
                    put("shard_templateId", shardTemplateId)
                })
            }

            put("item_value", buildJsonObject {
                put("price", price)
                put("ge_buy_limit", geBuyLimit)
                put("stock_market", stockMarket)
            })
            val actionObj = buildJsonObject {
                if (!inventoryActions.isJustNulls()) {
                    putJsonArray("inventory") {
                        inventoryActions.forEach {
                            add(it ?: "null")
                        }
                    }
                }
                if (!wornActions.isJustNulls()) {
                    putJsonArray("worn") {
                        wornActions.forEach {
                            add(it ?: "null")
                        }
                    }
                }
                if (!groundActions.isJustNulls()) {
                    putJsonArray("ground") {
                        groundActions.forEach {
                            add(it ?: "null")
                        }
                    }
                }
            }
            if (actionObj.isNotEmpty()) {
                put("actions", actionObj)
            }
            val p = params.toJsonObject()
            if (p.isNotEmpty()) {
                put("params", p)
            }
        }
    }
}