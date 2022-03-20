package com.rshub.definitions.loaders

import com.rshub.definitions.InventoryDefinition
import com.rshub.utilities.unsignedByte
import com.rshub.utilities.unsignedShort
import java.nio.ByteBuffer

class InventoryLoader {

    fun load(id: Int, data: ByteArray) : InventoryDefinition {
        val buffer = ByteBuffer.wrap(data)
        val def = InventoryDefinition(id)
        while(true) {
            when(buffer.unsignedByte) {
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

}