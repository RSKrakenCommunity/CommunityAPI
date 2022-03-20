package com.rshub.definitions.loaders

import com.rshub.definitions.VarbitDefinition
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class VarbitLoader {
    fun load(id: Int, buffer: ByteBuffer): VarbitDefinition {
        val def = VarbitDefinition(id)
        while (buffer.hasRemaining()) {
            val opcode = buffer.get().toInt() and 0xff
            if (opcode == 0) {
                break
            }
            when (opcode) {
                1 -> {
                    def.type = buffer.get().toInt() and 0xff
                    def.index = buffer.short.toInt() and 65535
                }
                2 -> {
                    def.lsb = buffer.get().toInt() and 0xff
                    def.msb = buffer.get().toInt() and 0xff
                }
                else -> error("Unknown opcode $opcode.")
            }
        }
        return def
    }
}