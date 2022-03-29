package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.definitions.DefinitionManager
import com.rshub.definitions.loaders.VarbitLoader
import org.junit.jupiter.api.Test

class VarbitForVarpTest {

    @Test
    fun `varbit for varp`() {
        val cache = DefinitionManager(CacheHelper.getFilesystem(), 2, 69, 0, VarbitLoader())

        val start = System.currentTimeMillis()

        repeat(1000) {
            cache.get(it)
        }

        val end = (System.currentTimeMillis() - start)

        println(end)
    }

}