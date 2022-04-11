package com.rshub.definitions.loaders

import com.rshub.definitions.StructDefinition
import com.rshub.utilities.unsignedByte
import java.nio.ByteBuffer

class StructLoader : Loader<StructDefinition> {
    override fun load(id: Int, buffer: ByteBuffer): StructDefinition {
        val def = newDefinition(id)
        while (true) {
            val opcode = buffer.unsignedByte
            if (opcode == 0)
                break
            if (opcode == 249) {
                def.param.parse(buffer)
            }
        }
        return def
    }

    override fun newDefinition(id: Int): StructDefinition {
        return StructDefinition(id)
    }
}