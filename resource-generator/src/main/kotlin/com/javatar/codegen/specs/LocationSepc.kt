package com.javatar.codegen.specs

import com.rshub.definitions.maps.WorldTile
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

object LocationSepc {

    private val json = Json { ignoreUnknownKeys = true }

    fun buildBankLocationFile(path: Path) {
        val file = FileSpec.builder("com.rshub.api.world", "BankLocations")
        file.addImport("com.rshub.definitions.maps", "WorldTile")

        val tsob = TypeSpec.objectBuilder("BankLocations")

        val con = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/banks.json")
        val data = String(con.openStream().readBytes())
        val bankLocs = json.decodeFromString<List<BankLocation>>(data)

        for (bankLoc in bankLocs) {
            val tile = bankLoc.tile
            tsob.addProperty(
                PropertySpec.builder(
                    bankLoc.name
                        .uppercase()
                        .replace(' ', '_'),
                    WorldTile::class
                ).addAnnotation(JvmStatic::class)
                    .initializer("WorldTile(${tile.x}, ${tile.y}, ${tile.z})")
                    .build()
            )
        }

        file.addType(tsob.build()).build().writeTo(path)

    }

    @Serializable
    data class BankLocation(val name: String, val tile: WorldTile)

    @JvmStatic
    fun main(args: Array<String>) {
        buildBankLocationFile(Paths.get("api/src/main/kotlin"))
    }

}