package com.rshub.definitions.loaders

import com.rshub.definitions.ParamDefinition
import com.rshub.definitions.cs2.ScriptVarType
import com.rshub.filesystem.ext.string
import com.rshub.filesystem.ext.unsignedSmart
import java.nio.ByteBuffer

class ParamLoader : Loader<ParamDefinition> {

    /*override val indexId: Int
        get() = Index.CONFIG
    override val archiveId: Int
        get() = 11
    override val sizeShift: Int
        get() = 1*/

    override fun load(id: Int, buffer: ByteBuffer): ParamDefinition {
        val def = ParamDefinition(id)
        def.decode(buffer)
        return def
    }

    private fun ParamDefinition.decode(buffer: ByteBuffer) {
        while (buffer.hasRemaining()) {
            val opcode: Int = buffer.get().toInt()
            if (opcode == 0) break
            when (opcode) {
                101 -> {
                    typeId = buffer.unsignedSmart
                    type = ScriptVarType.getById(typeId)
                }
                2 -> {
                    defaultInt = buffer.int
                }
                4 -> {
                    autoDisable = false
                }
                5 -> {
                    defaultString = buffer.string
                }
                else -> {}
            }
        }
    }

    override fun newDefinition(id: Int): ParamDefinition {
        return ParamDefinition(id)
    }

}