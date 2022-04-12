package com.rshub.api.world

import com.rshub.api.banking.BankLocation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

object LocationManager {
    private val json = Json { ignoreUnknownKeys = true }

    val banks = mutableMapOf<String, BankLocation>()

    fun fetchBankLocations(url: String) {
        val con = URL(url)
        val data = con.openStream().readBytes()
        banks.putAll(json.decodeFromString<List<BankLocation>>(String(data)).associateBy { it.name })
    }
}