package com.rshub.filesystem.test

import com.rshub.definitions.loaders.InventoryLoader
import com.rshub.filesystem.sqlite.SqliteFilesystem
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class InventoryDefinitionTest {

    @Test
    fun `read inventory definition`() {
        val fs = SqliteFilesystem(Paths.get("C:\\ProgramData\\Jagex\\RuneScape"))
        val configs = fs.getReferenceTable(2) ?: return
        val invDefs = configs.loadArchive(5) ?: return
        val invId = 95
        val data = invDefs.files[invId]?.data ?: return

        val loader = InventoryLoader()

        val inv = loader.load(invId, data)

        println(inv.id)
        println(inv.inventorySize)
        println(inv.size)
        println(inv.someIntArray1.toTypedArray().contentToString())
        println(inv.someIntArray2.toTypedArray().contentToString())

    }

}