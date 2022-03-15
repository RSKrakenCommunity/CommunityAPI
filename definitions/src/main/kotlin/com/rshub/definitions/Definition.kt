package com.rshub.definitions

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface Definition {

    val id: Int

    fun toJsonObject() : JsonObject {
        return buildJsonObject {
            put("definitionId", id)
        }
    }

}