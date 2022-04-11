package com.rshub.filesystem.test

import com.rshub.api.definitions.CacheHelper
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class StructTest {

    @Test
    fun `struct test`() {

        val structs = CacheHelper.allStructs()
            .filter { it.param.map != null }
            .filter { it.param.map!!.containsKey(2210) }
            .filter { it.param.map!![2210].toString().endsWith("tree", true) }
            .filter { it.param.map!![2212].toString().toInt() <= 99 }
            .filterNot { it.param.map!!.containsKey(2216) }

        val woodcuttingStructs = mutableListOf<WoodCuttingTree>()

        for (struct in structs.sortedBy { it.param.map!![2212].toString().toInt() }) {
            val name = struct.param.map!![2210]!!
            val level = struct.param.map!![2212]!!.toString().toInt()
            val itemId = struct.param.map!![2213]!!.toString().toInt()
            println(struct.id)
            println(name)
            println(level)
            println("---------------------------------")
            woodcuttingStructs.add(WoodCuttingTree(name.toString(), level, itemId))
        }

        val json = Json { prettyPrint = true }

        Files.write(Paths.get("C:\\Users\\david\\IdeaProjects\\KrakenCommunityPages").resolve("woodcutting.json"), json.encodeToString(woodcuttingStructs).toByteArray())

    }

    @Serializable
    data class WoodCuttingTree(val name: String, val level: Int, val itemId: Int)

}