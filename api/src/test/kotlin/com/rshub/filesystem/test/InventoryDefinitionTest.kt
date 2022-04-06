package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import com.rshub.definitions.loaders.InventoryLoader
import com.rshub.filesystem.sqlite.SqliteFilesystem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
import java.nio.file.Paths

class InventoryDefinitionTest {

    @Test
    fun `read inventory definition`() {
        val fs = SqliteFilesystem(Paths.get("C:\\ProgramData\\Jagex\\RuneScape"))
        val configs = fs.getReferenceTable(2) ?: return
        val invDefs = configs.loadArchive(5) ?: return
        val invId = 93
        val data = invDefs.files[invId]?.data ?: return

        val loader = InventoryLoader()

        println(data.size)
        val inv = loader.load(invId, ByteBuffer.wrap(data))

        println(inv.id)
        println(inv.inventorySize)
        println(inv.size)
        println(inv.someIntArray1.toTypedArray().contentToString())
        println(inv.someIntArray2.toTypedArray().contentToString())

    }

    @Test
    fun `object dump`() {
        val def = CacheHelper.getObject(28693)
        val json = Json { prettyPrint = true }
        println(json.encodeToString(def.toJsonObject()))

    }

}