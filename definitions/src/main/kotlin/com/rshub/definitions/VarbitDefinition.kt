package com.rshub.definitions

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

data class VarbitDefinition(
    override val id: Int,
    var index: Int = -1,
    var type: Int = -1,
    var msb: Int = -1,
    var lsb: Int = -1
) : Definition {

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("varbitId", id)
            put("varp", index)
            put("lsb", lsb)
            put("msb", msb)
            var bits = msb - lsb
            if (bits == 0)
                bits = 1
            put("kraken", buildJsonObject {
                put("ConVar", "Client.getConVarById($index);")
                put("Masked", "Client.getConVarById($index).getValueMasked($lsb, $bits);")
            })
        }
    }
}