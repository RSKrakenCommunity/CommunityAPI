package com.rshub.definitions.loaders

import com.rshub.definitions.maps.ObjectType
import com.rshub.definitions.objects.ObjectDefinition
import com.rshub.utilities.*
import java.nio.ByteBuffer
import java.util.*

class ObjectLoader : Loader<ObjectDefinition> {
    override fun load(id: Int, buffer: ByteBuffer): ObjectDefinition {
        val def = ObjectDefinition(id)
        try {
            def.decode(buffer)
        } catch (e: Exception) {
            return def
        }
        return def
    }

    private var previousOpcode: Int = -1

    private fun ObjectDefinition.decode(stream: ByteBuffer) {
        while (stream.hasRemaining()) {
            val opcode: Int = stream.unsignedByte
            if (opcode == 0) break
            if (opcode == 1) {
                val i_4_: Int = stream.unsignedByte
                this.types = arrayOfNulls<ObjectType?>(i_4_)
                this.modelIds = arrayOfNulls<IntArray?>(i_4_)
                for (i_5_ in 0 until i_4_) {
                    this.types[i_5_] = ObjectType.forId(stream.get().toInt())
                    val i_6_: Int = stream.unsignedByte
                    this.modelIds[i_5_] = IntArray(i_6_)
                    for (i_7_ in 0 until i_6_) this.modelIds[i_5_]?.set(i_7_, stream.getSmartInt())
                }
            } else if (opcode == 2) {
                this.name = stream.string
            } else if (opcode == 14) {
                this.sizeX = stream.unsignedByte
            } else if (15 == opcode) {
                this.sizeY = stream.unsignedByte
            } else if (17 == opcode) {
                this.solidType = 0
                this.blocksProjectile = false
            } else if (18 == opcode) {
                this.blocksProjectile = false
            } else if (opcode == 19) {
                this.interactable = stream.unsignedByte
            } else if (21 == opcode) {
                this.groundContoured = 1.toByte()
            } else if (22 == opcode) {
                this.delayShading = true
            } else if (opcode == 23) {
                this.occludes = 1
            } else if (opcode == 24) {
                val i_8_: Int = stream.getSmartInt()
                if (i_8_ != -1) this.animations = intArrayOf(i_8_)
            } else if (opcode == 27) {
                this.solidType = 1
            } else if (opcode == 28) {
                this.decorDisplacement = stream.unsignedByte shl 2
            } else if (opcode == 29) {
                this.ambient = stream.get()
            } else if (39 == opcode) {
                this.contrast = stream.get()
            } else if (opcode in 30..34) {
                this.options[opcode - 30] = stream.string
            } else if (40 == opcode) {
                val i_9_: Int = stream.unsignedByte
                this.originalColors = ShortArray(i_9_)
                this.modifiedColors = ShortArray(i_9_)
                for (i_10_ in 0 until i_9_) {
                    this.originalColors[i_10_] = (stream.short.toInt()).toShort()
                    this.modifiedColors[i_10_] = (stream.short.toInt()).toShort()
                }
            } else if (opcode == 41) {
                val i_11_: Int = stream.unsignedByte
                this.originalTextures = ShortArray(i_11_)
                this.modifiedTextures = ShortArray(i_11_)
                for (i_12_ in 0 until i_11_) {
                    this.originalTextures[i_12_] = (stream.short.toInt()).toShort()
                    this.modifiedTextures[i_12_] = (stream.short.toInt()).toShort()
                }
            } else if (opcode == 42) {
                val i_13_: Int = stream.unsignedByte
                this.aByteArray5641 = ByteArray(i_13_)
                for (i_14_ in 0 until i_13_) this.aByteArray5641[i_14_] = stream.get()
            } else if (opcode == 44) {
                stream.short
            } else if (opcode == 45) {
                stream.short
            } else if (opcode == 62) {
                this.inverted = true
            } else if (opcode == 64) {
                this.castsShadow = false
            } else if (65 == opcode) {
                this.scaleX = stream.short.toInt()
            } else if (opcode == 66) {
                this.scaleY = stream.short.toInt()
            } else if (67 == opcode) {
                this.scaleZ = stream.short.toInt()
            } else if (opcode == 69) {
                this.accessBlockFlag = stream.unsignedByte
            } else if (70 == opcode) {
                this.offsetX = stream.short.toInt() shl 2
            } else if (opcode == 71) {
                this.offsetY = stream.short.toInt() shl 2
            } else if (opcode == 72) {
                this.offsetZ = stream.short.toInt() shl 2
            } else if (73 == opcode) {
                this.obstructsGround = true
            } else if (opcode == 74) {
                this.ignoreAltClip = true
            } else if (opcode == 75) {
                this.supportsItems = stream.unsignedByte
            } else if (77 == opcode || 92 == opcode) {
                this.varpBit = stream.unsignedShort
                if (65535 == this.varpBit) this.varpBit = -1
                this.varp = stream.unsignedShort
                if (this.varp == 65535) this.varp = -1
                var objectId = -1
                if (opcode == 92) objectId = stream.getSmartInt()
                val transforms: Int = stream.unsignedSmart
                this.transformTo = IntArray(transforms + 2)
                for (i in 0..transforms) this.transformTo[i] = stream.getSmartInt()
                this.transformTo[1 + transforms] = objectId
            } else if (78 == opcode) {
                this.ambientSoundId = stream.short.toInt()
                this.ambientSoundHearDistance = stream.unsignedByte
            } else if (79 == opcode) {
                this.anInt5667 = stream.short.toInt()
                this.anInt5698 = stream.short.toInt()
                this.ambientSoundHearDistance = stream.unsignedByte
                val i_18_: Int = stream.unsignedByte
                this.audioTracks = IntArray(i_18_)
                for (i_19_ in 0 until i_18_) this.audioTracks[i_19_] = stream.short.toInt()
            } else if (81 == opcode) {
                this.groundContoured = 2.toByte()
                this.anInt5654 = stream.unsignedByte * 256
            } else if (opcode == 82) {
                this.hidden = true
            } else if (88 == opcode) {
                this.aBool5703 = false
            } else if (opcode == 89) {
                this.aBool5702 = false
            } else if (91 == opcode) {
                this.members = true
            } else if (93 == opcode) {
                this.groundContoured = 3.toByte()
                this.anInt5654 = stream.short.toInt()
            } else if (opcode == 94) {
                this.groundContoured = 4.toByte()
            } else if (95 == opcode) {
                this.groundContoured = 5.toByte()
                this.anInt5654 = stream.short.toInt()
            } else if (97 == opcode) {
                this.adjustMapSceneRotation = true
            } else if (98 == opcode) {
                this.hasAnimation = true
            } else if (99 == opcode) {
                this.anInt5705 = stream.unsignedByte
                this.anInt5665 = stream.short.toInt()
            } else if (opcode == 100) {
                this.anInt5670 = stream.unsignedByte
                this.anInt5666 = stream.short.toInt()
            } else if (101 == opcode) {
                this.mapSpriteRotation = stream.unsignedByte
            } else if (opcode == 102) {
                this.mapSpriteId = stream.short.toInt()
            } else if (opcode == 103) {
                this.occludes = 0
            } else if (104 == opcode) {
                this.ambientSoundVolume = stream.unsignedByte
            } else if (opcode == 105) {
                this.flipMapSprite = true
            } else if (106 == opcode) {
                val i_20_: Int = stream.unsignedByte
                var i_21_ = 0
                this.animations = IntArray(i_20_)
                this.animProbs = IntArray(i_20_)
                for (i_22_ in 0 until i_20_) {
                    this.animations[i_22_] = stream.getSmartInt()
                    this.animProbs[i_22_] = stream.unsignedByte
                    i_21_ += this.animProbs[i_22_]
                }
                for (i_23_ in 0 until i_20_) this.animProbs[i_23_] = this.animProbs[i_23_] * 65535 / i_21_
            } else if (opcode == 107) {
                this.mapIcon = stream.short.toInt()
            } else if (opcode >= 150 && opcode < 155) {
                this.options[opcode - 150] = stream.string
            } else if (160 == opcode) {
                val i_24_: Int = stream.unsignedByte
                this.anIntArray5707 = IntArray(i_24_)
                for (i_25_ in 0 until i_24_) this.anIntArray5707[i_25_] = stream.short.toInt()
            } else if (162 == opcode) {
                this.groundContoured = 3.toByte()
                this.anInt5654 = stream.int
            } else if (163 == opcode) {
                this.aByte5644 = stream.get()
                this.aByte5642 = stream.get()
                this.aByte5646 = stream.get()
                this.aByte5634 = stream.get()
            } else if (164 == opcode) {
                this.anInt5682 = stream.short
            } else if (165 == opcode) {
                this.anInt5683 = stream.short
            } else if (166 == opcode) {
                this.anInt5710 = stream.short
            } else if (167 == opcode) {
                this.anInt5704 = stream.short.toInt()
            } else if (168 == opcode) {
                this.aBool5696 = true
            } else if (169 == opcode) {
                this.aBool5700 = true
            } else if (opcode == 170) {
                this.anInt5684 = stream.unsignedSmart
            } else if (opcode == 171) {
                this.anInt5658 = stream.unsignedSmart
            } else if (opcode == 173) {
                this.anInt5708 = stream.short.toInt()
                this.anInt5709 = stream.short.toInt()
            } else if (177 == opcode) {
                this.aBool5699 = true
            } else if (178 == opcode) {
                this.anInt5694 = stream.unsignedByte
            } else if (186 == opcode) {
                val unk: Int = stream.unsignedByte
            } else if (188 == opcode) {
                val unk = true
            } else if (189 == opcode) {
                this.aBool5711 = true
            } else if (opcode in 190..194) {
                if (this.actionCursors == null) {
                    this.actionCursors = IntArray(5)
                    Arrays.fill(this.actionCursors, -1)
                }
                this.actionCursors!![opcode - 190] = stream.short.toInt()
            } else if (opcode == 196) {
                val unk: Int = stream.unsignedByte
            } else if (opcode == 197) {
                val unk: Int = stream.unsignedByte
            } else if (opcode == 198) {
                val unk = true
            } else if (opcode == 199) {
                val unk = true
            } else if (opcode == 201) {
                val unk0: Int = stream.unsignedSmart
                val unk1: Int = stream.unsignedSmart
                val unk2: Int = stream.unsignedSmart
                val unk3: Int = stream.unsignedSmart
                val unk4: Int = stream.unsignedSmart
                val unk5: Int = stream.unsignedSmart
            } else if (opcode == 202) {
                val unk: Int = stream.unsignedSmart
            } else if (opcode == 203) {
                //IDK
            } else if (249 == opcode) {
                this.params.parse(stream)
            } else {
                throw RuntimeException(
                    "Missing Object opcode: $opcode - $previousOpcode - $id\n${
                        stream.toByteArray().contentToString()
                    }"
                )
            }
            previousOpcode = opcode
        }
    }

    override fun newDefinition(id: Int): ObjectDefinition {
        return ObjectDefinition(id)
    }
}