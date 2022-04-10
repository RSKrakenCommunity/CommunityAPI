package com.rshub.api.pathing

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


object ResourceUpdater {

    fun update() {
        downloadWeb()
        downloadLocations()
    }

    fun downloadWeb() {
        val path = Paths.get(System.getProperty("user.home"))
            .resolve("kraken-plugins")
            .resolve("web.json")
        val file = path.toFile()
        val url = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/web.json")
        val con = url.openConnection()
        if (!file.exists() || file.lastModified() != con.lastModified) {
            val input = con.getInputStream()
            Files.copy(
                input,
                Paths.get(System.getProperty("user.home"))
                    .resolve("kraken-plugins")
                    .resolve("web.json"),
                StandardCopyOption.REPLACE_EXISTING
            )
            file.setLastModified(con.lastModified)
        }
    }

    fun downloadLocations() {
        val path = Paths.get(System.getProperty("user.home"))
            .resolve("kraken-plugins")
            .resolve("locations.json")
        val file = path.toFile()
        val url = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/locations.json")
        val con = url.openConnection()
        if (!file.exists() || file.lastModified() != con.lastModified) {
            val input = con.getInputStream()
            Files.copy(
                input,
                path,
                StandardCopyOption.REPLACE_EXISTING
            )
            file.setLastModified(con.lastModified)
        }
    }

}