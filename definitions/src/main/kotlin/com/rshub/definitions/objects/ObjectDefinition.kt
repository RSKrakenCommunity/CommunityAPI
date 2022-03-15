package com.rshub.definitions.objects

import com.rshub.buffer.exts.isJustNulls
import com.rshub.definitions.Definition
import com.rshub.definitions.Params
import kotlinx.serialization.json.*

class ObjectDefinition(override val id: Int) : Definition {

    var types: Array<ObjectType?> = arrayOfNulls(0)
    var modelIds: Array<IntArray?> = arrayOfNulls(0)
    var name = ""
    var sizeX = 1
    var sizeY = 1
    var clipType = 2
    var blocks = false
    var interactable = 0
    var groundContoured: Byte = 0
    var delayShading = false
    var occludes = 0
    var animations: IntArray = intArrayOf()
    var decorDisplacement = 0
    var ambient: Byte = 0
    var contrast: Byte = 0
    var options = arrayOfNulls<String>(5)
    var originalColors: ShortArray = shortArrayOf()
    var modifiedColors: ShortArray = shortArrayOf()
    var originalTextures: ShortArray = shortArrayOf()
    var modifiedTextures: ShortArray = shortArrayOf()
    var aByteArray5641: ByteArray = byteArrayOf()
    var inverted = false
    var castsShadow = false
    var scaleX = 0
    var scaleY = 0
    var scaleZ = 0
    var accessBlockFlag = 0
    var offsetX = 0
    var offsetY = 0
    var offsetZ = 0
    var obstructsGround = false
    var supportsItems = 0
    var ignoreAltClip = false
    var varpBit = -1
    var varp = -1
    var transformTo: IntArray = intArrayOf()
    var ambientSoundId = 0
    var ambientSoundHearDistance = 0
    var anInt5667 = 0
    var anInt5698 = 0
    var audioTracks: IntArray = intArrayOf()
    var anInt5654 = 0
    var hidden = false
    var aBool5703 = true
    var aBool5702 = true
    var members = false
    var adjustMapSceneRotation = false
    var hasAnimation = false
    var anInt5705 = 0
    var anInt5665 = 0
    var anInt5670 = 0
    var anInt5666 = 0
    var mapSpriteRotation = 0
    var mapSpriteId = 0
    var ambientSoundVolume = 0
    var flipMapSprite = false
    var animProbs: IntArray = intArrayOf()
    var mapIcon = 0
    var anIntArray5707: IntArray = intArrayOf()
    var aByte5644: Byte = 0
    var aByte5642: Byte = 0
    var aByte5646: Byte = 0
    var aByte5634: Byte = 0
    var anInt5682: Short = 0
    var anInt5683: Short = 0
    var anInt5710: Short = 0
    var anInt5704 = 0
    var aBool5696 = false
    var aBool5700 = false
    var anInt5684 = 0
    var anInt5658 = 0
    var anInt5708 = 0
    var anInt5709 = 0
    var aBool5699 = false
    var anInt5694 = 0
    var aBool5711 = false
    var params = Params()
    var actionCursors: IntArray? = null

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("objectId", id)
            put("name", name)
            put("hidden", hidden)
            put("supports_items", supportsItems)
            put("interactable", interactable)

            put("vars", buildJsonObject {
                put("varp", varp)
                put("varbit", varpBit)
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
            }
            if (actionsObj.isNotEmpty()) {
                put("actions", actionsObj)
            }

            val p = params.toJsonObject()
            if (p.isNotEmpty()) {
                put("params", p)
            }
        }
    }
}