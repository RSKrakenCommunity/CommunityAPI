package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import org.junit.jupiter.api.Test

class ObjectLoaderTest {

    @Test
    fun `load object test`() {
        val def = CacheHelper.getObject(12269)

        println(def.name)
        println(def.options.contentToString())
        println(def.blocksProjectile)

        def.types.forEach {
            println(it)
        }

    }

}