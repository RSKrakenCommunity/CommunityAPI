package com.rshub.definitions.loaders

import com.rshub.definitions.NpcDefinition
import com.rshub.utilities.*
import java.nio.ByteBuffer
import java.util.*

class NpcLoader : Loader<NpcDefinition> {

    override fun load(id: Int, buffer: ByteBuffer): NpcDefinition {
        val def = NpcDefinition(id)
        def.decode(buffer)
        return def
    }

    private fun NpcDefinition.decode(stream: ByteBuffer) {
        val def = this
        while (stream.hasRemaining()) {
            val opcode: Int = stream.unsignedByte
            if (opcode == 0) break
            when (opcode) {
                1 -> {
                    val i_4: Int = stream.unsignedByte
                    def.modelIds = IntArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.modelIds[i_5] = stream.getSmartInt()
                    }
                }
                2 -> {
                    def.name = stream.string
                }
                12 -> {
                    def.size = stream.unsignedByte
                }
                in 30..34 -> {
                    def.options[opcode - 30] = stream.string
                }
                40 -> {
                    val i_4: Int = stream.unsignedByte
                    def.originalColors = ShortArray(i_4)
                    def.modifiedColors = ShortArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.originalColors[i_5] = stream.short
                        def.modifiedColors[i_5] = stream.short
                    }
                }
                41 -> {
                    val i_4: Int = stream.unsignedByte
                    def.originalTextures = ShortArray(i_4)
                    def.modifiedTextures = ShortArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.originalTextures[i_5] = stream.short
                        def.modifiedTextures[i_5] = stream.short
                    }
                }
                42 -> {
                    val i_4: Int = stream.unsignedByte
                    def.recolourPalette = ByteArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.recolourPalette[i_5] = stream.get()
                    }
                }
                44 -> {
                    stream.short
                }
                45 -> {
                    stream.short
                }
                60 -> {
                    val i_4: Int = stream.unsignedByte
                    def.headModels = IntArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.headModels[i_5] = stream.getSmartInt()
                    }
                }
                93 -> {
                    def.drawMapdot = false
                }
                95 -> {
                    def.combatLevel = stream.short.toInt()
                }
                97 -> {
                    def.resizeX = stream.short.toInt()
                }
                98 -> {
                    def.resizeY = stream.short.toInt()
                }
                99 -> {
                    def.aBool4904 = true
                }
                100 -> {
                    def.ambient = stream.get()
                }
                101 -> {
                    def.contrast = stream.get()
                }
                102 -> {
                    def.headIcons = stream.masked
                }
                103 -> {
                    def.rotation = stream.short.toInt()
                }
                106, 118 -> {
                    def.varbit = stream.short.toInt()
                    if (def.varbit == 65535) {
                        def.varbit = -1
                    }
                    def.varp = stream.short.toInt()
                    if (def.varp == 65535) {
                        def.varp = -1
                    }
                    var defaultId = -1
                    if (opcode == 118) {
                        defaultId = stream.short.toInt()
                        if (defaultId == 65535) {
                            defaultId = -1
                        }
                    }
                    val size: Int = stream.unsignedSmart
                    def.transformTo = IntArray(size + 2)
                    for (i in 0 until size + 1) {
                        def.transformTo[i] = stream.short.toInt()
                        if (def.transformTo[i] === 65535) {
                            def.transformTo[i] = -1
                        }
                    }
                    def.transformTo[size + 1] = defaultId
                }
                107 -> {
                    def.visible = false
                }
                109 -> {
                    def.isClickable = false
                }
                111 -> {
                    def.animateIdle = false
                }
                113 -> {
                    def.aShort4874 = stream.short.toInt()
                    def.aShort4897 = stream.short.toInt()
                }
                114 -> {
                    def.aByte4883 = stream.get()
                    def.aByte4899 = stream.get()
                }
                119 -> {
                    def.walkMask = stream.get()
                }
                121 -> {
                    def.modelTranslation = arrayOfNulls(def.modelIds.size)
                    val i_4: Int = stream.unsignedByte
                    for (i_5 in 0 until i_4) {
                        val translations = IntArray(4)
                        translations[0] = stream.get().toInt()
                        translations[1] = stream.get().toInt()
                        translations[2] = stream.get().toInt()
                        translations[3] = stream.get().toInt()
                    }
                }
                123 -> {
                    def.height = stream.short.toInt()
                }
                125 -> {
                    def.respawnDirection = stream.get()
                }
                127 -> {
                    def.basId = stream.short.toInt()
                }
                128 -> {
                    def.movementType = NpcDefinition.MovementType.forId(stream.unsignedByte)
                }
                134 -> {
                    def.walkingAnimation = stream.short.toInt()
                    if (def.walkingAnimation == 65535) {
                        def.walkingAnimation = -1
                    }
                    def.rotate180Animation = stream.short.toInt()
                    if (def.rotate180Animation == 65535) {
                        def.rotate180Animation = -1
                    }
                    def.rotate90RightAnimation = stream.short.toInt()
                    if (def.rotate90RightAnimation == 65535) {
                        def.rotate90RightAnimation = -1
                    }
                    def.rotate90LeftAnimation = stream.short.toInt()
                    if (def.rotate90LeftAnimation == 65535) {
                        def.rotate90LeftAnimation = -1
                    }
                    def.specialByte = stream.unsignedByte
                }
                135 -> {
                    def.anInt4875 = stream.unsignedByte
                    def.anInt4873 = stream.short.toInt()
                }
                136 -> {
                    def.anInt4854 = stream.unsignedByte
                    def.anInt4861 = stream.short.toInt()
                }
                137 -> {
                    def.attackOpCursor = stream.short.toInt()
                }
                138 -> {
                    def.armyIcon = stream.getSmartInt()
                }
                140 -> {
                    def.anInt4909 = stream.unsignedByte
                }
                141 -> {
                    def.aBool4884 = true
                }
                142 -> {
                    def.mapIcon = stream.short.toInt()
                }
                143 -> {
                    def.aBool4890 = true
                }
                in 150..154 -> {
                    def.membersOptions[opcode - 150] = stream.string
                }
                155 -> {
                    def.aByte4868 = stream.get()
                    def.aByte4869 = stream.get()
                    def.aByte4905 = stream.get()
                    def.aByte4871 = stream.get()
                }
                158 -> {
                    def.aByte4916 = 1
                }
                159 -> {
                    def.aByte4916 = 0
                }
                160 -> {
                    val i_4: Int = stream.unsignedByte
                    def.quests = IntArray(i_4)
                    for (i_5 in 0 until i_4) {
                        def.quests[i_5] = stream.short.toInt()
                    }
                }
                162 -> {
                    def.aBool4872 = true
                }
                163 -> {
                    def.anInt4917 = stream.unsignedByte
                }
                164 -> {
                    def.anInt4911 = stream.short.toInt()
                    def.anInt4919 = stream.short.toInt()
                }
                165 -> {
                    def.anInt4913 = stream.unsignedByte
                }
                168 -> {
                    def.anInt4908 = stream.unsignedByte
                }
                169 -> {
                    def.aBool4920 = false
                }
                in 170..174 -> {
                    if (def.actionCursors == null) {
                        def.actionCursors = IntArray(5)
                        Arrays.fill(def.actionCursors!!, -1)
                    }
                    def.actionCursors!![opcode - 170] = stream.short.toInt()
                }
                178 -> {
                    val unk = true
                }
                179 -> {
                    val unk0: Int = stream.unsignedSmart
                    val unk1: Int = stream.unsignedSmart
                    val unk2: Int = stream.unsignedSmart
                    val unk3: Int = stream.unsignedSmart
                    val unk4: Int = stream.unsignedSmart
                    val unk5: Int = stream.unsignedSmart
                }
                180 -> {
                    System.err.println("Missing opcode 180")
                }
                181 -> {
                    System.err.println("Missing opcode 181")
                }
                182 -> {
                    val unk = true
                }
                183 -> {
                    stream.skip(1)
                }
                184 -> {
                    stream.skip(1)
                }
                249 -> {
                    def.params.parse(stream)
                }
                else -> {
                    throw RuntimeException("Missing NPC opcode: $opcode")
                }
            }
        }
    }

    override fun newDefinition(id: Int): NpcDefinition {
        return NpcDefinition(id)
    }


}