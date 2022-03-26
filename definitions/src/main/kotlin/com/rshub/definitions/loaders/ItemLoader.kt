package com.rshub.definitions.loaders

import com.rshub.definitions.ItemDefinition
import com.rshub.utilities.getSmartInt
import com.rshub.utilities.string
import com.rshub.utilities.unsignedByte
import com.rshub.utilities.unsignedShort
import java.nio.ByteBuffer
import java.util.*

class ItemLoader : Loader<ItemDefinition> {

    override fun load(id: Int, buffer: ByteBuffer): ItemDefinition {
        val def = newDefinition(id)
        def.decode(buffer)
        return def
    }

    private fun ItemDefinition.decode(buffer: ByteBuffer) {
        val def = this
        while (buffer.hasRemaining()) {
            val opcode: Int = buffer.unsignedByte
            if (opcode == 0) {
                break
            } else if (opcode == 1) {
                def.modelId = buffer.getSmartInt()
            } else if (opcode == 2) {
                def.name = buffer.string
            } else if (opcode == 3) {
                def.buffEffect = buffer.string // examine
            } else if (opcode == 4) {
                def.modelZoom = buffer.unsignedShort
            } else if (opcode == 5) {
                def.modelRotationX = buffer.unsignedShort
            } else if (opcode == 6) {
                def.modelRotationY = buffer.unsignedShort
            } else if (opcode == 7) {
                def.modelOffsetX = buffer.unsignedShort
                if (def.modelOffsetX > 32767) {
                    def.modelOffsetX -= 65536
                }
            } else if (opcode == 8) {
                def.modelOffsetY = buffer.unsignedShort
                if (def.modelOffsetY > 32767) {
                    def.modelOffsetY -= 65536
                }
            } else if (opcode == 11) {
                def.stackable = 1
            } else if (opcode == 12) {
                def.price = buffer.int
            } else if (opcode == 13) {
                def.equipmentSlot = buffer.get()
            } else if (opcode == 14) {
                def.equipmentType = buffer.get()
            } else if (opcode == 15) {
                // TODO bool
            } else if (opcode == 16) {
                def.members = true
            } else if (opcode == 18) {
                def.unused = buffer.unsignedShort
            } else if (opcode == 23) {
                def.maleModel1 = buffer.getSmartInt()
            } else if (opcode == 24) {
                def.maleModel2 = buffer.getSmartInt()
            } else if (opcode == 25) {
                def.femaleModel1 = buffer.getSmartInt()
            } else if (opcode == 26) {
                def.femaleModel2 = buffer.getSmartInt()
            } else if (opcode == 27) {
                def.equipmentType2 = buffer.get()
            } else if (opcode in 30..34) {
                def.groundActions[opcode - 30] = buffer.string
            } else if (opcode in 35..39) {
                def.inventoryActions[opcode - 35] = buffer.string
            } else if (opcode == 40) {
                val count: Int = buffer.unsignedByte
                def.originalColors = ShortArray(count)
                def.replacementColors = ShortArray(count)
                for (idx in 0 until count) {
                    def.originalColors[idx] = (buffer.unsignedShort).toShort()
                    def.replacementColors[idx] = (buffer.unsignedShort).toShort()
                }
            } else if (opcode == 41) {
                val count: Int = buffer.unsignedByte
                def.originalTextures = ShortArray(count)
                def.replacementTextures = ShortArray(count)
                for (idx in 0 until count) {
                    def.originalTextures[idx] = (buffer.unsignedShort).toShort()
                    def.replacementTextures[idx] = (buffer.unsignedShort).toShort()
                }
            } else if (opcode == 42) {
                val count: Int = buffer.unsignedByte
                def.recolorPalette = ByteArray(count)
                for (index in 0 until count) {
                    def.recolorPalette[index] = buffer.get()
                }
            } else if (opcode == 43) {
                buffer.int
            } else if (opcode == 44) {
                buffer.short.toInt()
            } else if (opcode == 45) {
                buffer.short.toInt()
            } else if (opcode == 65) {
                def.stockMarket = true
            } else if (opcode == 69) {
                def.geBuyLimit = buffer.int
            } else if (opcode == 78) {
                def.maleModel3 = buffer.getSmartInt()
            } else if (opcode == 79) {
                def.femaleModel3 = buffer.getSmartInt()
            } else if (opcode == 90) {
                def.maleHeadModel1 = buffer.getSmartInt()
            } else if (opcode == 91) {
                def.femaleHeadModel1 = buffer.getSmartInt()
            } else if (opcode == 92) {
                def.maleHeadModel2 = buffer.getSmartInt()
            } else if (opcode == 93) {
                def.femaleHeadModel2 = buffer.getSmartInt()
            } else if (opcode == 94) {
                def.category = buffer.short
            } else if (opcode == 95) {
                def.modelAngleZ = buffer.unsignedShort
            } else if (opcode == 96) {
                def.searchable = (buffer.unsignedByte).toByte()
            } else if (opcode == 97) {
                def.notedItemId = buffer.unsignedShort
            } else if (opcode == 98) {
                def.notedTemplate = buffer.unsignedShort
            } else if (opcode in 100..109) {
                if (def.stackIds == null) {
                    def.stackAmounts = IntArray(10)
                    def.stackIds = IntArray(10)
                }
                def.stackIds!![opcode - 100] = buffer.unsignedShort
                def.stackAmounts?.set(opcode - 100, buffer.unsignedShort)
            } else if (opcode == 110) {
                def.resizeX = buffer.unsignedShort
            } else if (opcode == 111) {
                def.resizeY = buffer.unsignedShort
            } else if (opcode == 112) {
                def.resizeZ = buffer.unsignedShort
            } else if (opcode == 113) {
                def.ambient = buffer.get()
            } else if (opcode == 114) {
                def.contrast = buffer.get() * 5
            } else if (opcode == 115) {
                def.teamId = (buffer.unsignedByte).toByte()
            } else if (opcode == 121) {
                def.lentItemId = buffer.unsignedShort
            } else if (opcode == 122) {
                def.lendTemplate = buffer.unsignedShort
            } else if (opcode == 125) {
                def.maleModelOffsetX = buffer.get().toInt() shl 2
                def.maleModelOffsetY = buffer.get().toInt() shl 2
                def.maleModelOffsetZ = buffer.get().toInt() shl 2
            } else if (opcode == 126) {
                def.femaleModelOffsetX = buffer.get().toInt() shl 2
                def.femaleModelOffsetY = buffer.get().toInt() shl 2
                def.femaleModelOffsetZ = buffer.get().toInt() shl 2
            } else if (opcode == 127 || opcode == 128 || opcode == 129 || opcode == 130) {
                buffer.get()
                buffer.short.toInt()
            } else if (opcode == 132) {
                val count: Int = buffer.unsignedByte
                def.questIds = IntArray(count)
                for (index in 0 until count) {
                    def.questIds[index] = buffer.unsignedShort
                }
            } else if (opcode == 134) {
                def.pickSizeShift = buffer.unsignedByte
            } else if (opcode == 139) {
                def.bindId = buffer.unsignedShort
            } else if (opcode == 140) {
                def.boundTemplate = buffer.unsignedShort
            } else if (opcode in 142..146) {
                if (def.groundCursors == null) {
                    def.groundCursors = IntArray(6)
                    Arrays.fill(def.groundCursors, -1)
                }
                def.groundCursors!![opcode - 142] = buffer.unsignedShort
            } else if (opcode == 147) {
                buffer.short.toInt()
            } else if (opcode in 150..154) {
                if (def.inventoryCursors == null) {
                    def.inventoryCursors = IntArray(5)
                    Arrays.fill(def.inventoryCursors, -1)
                }
                def.inventoryCursors!![opcode - 150] = buffer.unsignedShort
            } else if (opcode == 157) {
                def.randomizeGroundPos = true
            } else if (opcode == 161) {
                def.shardItemId = buffer.unsignedShort
            } else if (opcode == 162) {
                def.shardTemplateId = buffer.unsignedShort
            } else if (opcode == 163) {
                def.shardCombineAmount = buffer.short
            } else if (opcode == 164) {
                def.shardName = buffer.string
            } else if (opcode == 165) {
                def.neverStackable = true
            } else if (opcode == 167) {
                // TODO bool
            } else if (opcode == 168) {
                // TODO bool
            } else if (opcode == 242) {
                buffer.getSmartInt()
                buffer.getSmartInt()
            } else if (opcode in 243..248) {
                buffer.getSmartInt()
            } else if (opcode == 249) {
                def.params.parse(buffer)
            } else {
                throw IllegalArgumentException("Invalid EnumDefinition opcode $opcode")
            }
        }
        /*if (def.notedTemplate != -1) def.toNote()
        if (def.lendTemplate != -1) def.toLend()
        if (def.boundTemplate != -1) def.toBind()*/
        def.loadEquippedOps()
    }

    override fun newDefinition(id: Int): ItemDefinition {
        return ItemDefinition(id)
    }
}