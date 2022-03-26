package com.rshub.definitions.loaders

import com.rshub.definitions.InventoryDefinition
import com.rshub.utilities.unsignedByte
import com.rshub.utilities.unsignedShort
import java.nio.ByteBuffer

class InventoryLoader : Loader<InventoryDefinition> {
    override fun load(id: Int, buffer: ByteBuffer): InventoryDefinition {
        val def = newDefinition(id)
        while (true) {
            when (buffer.unsignedByte) {
                2 -> {
                    def.inventorySize = buffer.unsignedShort
                }
                4 -> {
                    def.size = buffer.unsignedByte
                    repeat(def.size) {
                        def.someIntArray1.add(buffer.unsignedShort)
                        def.someIntArray2.add(buffer.unsignedShort)
                    }
                }
                0 -> break
            }
        }
        return def
    }

    override fun newDefinition(id: Int): InventoryDefinition {
        return InventoryDefinition(id)
    }

}