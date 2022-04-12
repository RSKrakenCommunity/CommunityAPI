package com.rshub.filesystem.test

import com.rshub.api.pathing.ResourceUpdater
import com.rshub.definitions.maps.WorldTile
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.jupiter.api.Test
import java.net.URL

class ResourceDownloadTest {

    @Test
    fun `download test`() {
        ResourceUpdater.downloadWeb()
    }

    @Test
    fun `stream resource`() {
        val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
        val url = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/banks.json")
        val banks = json.decodeFromString<List<Bank>>(String(url.openStream().readBytes()))
        banks.forEach {
            println(it)
        }
    }

    @Serializable
    data class Bank(val name: String, val tile: WorldTile)

}