package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import org.junit.jupiter.api.Test

class RegionLoadTest {

    @Test
    fun `load region edgeville`() {
        val edge = CacheHelper.getMap(12342)

        println(edge.first.tileFlags.size)
        println(edge.second.objects.size)

    }

}