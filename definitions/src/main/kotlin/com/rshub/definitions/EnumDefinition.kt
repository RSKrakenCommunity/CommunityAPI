package com.rshub.definitions

import com.rshub.definitions.cs2.ScriptVarType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class EnumDefinition(override val id: Int) : Definition {

    var keyType: ScriptVarType? = ScriptVarType.INT
    var valueType: ScriptVarType? = ScriptVarType.INT
    var defaultInt = 0
    var defaultString: String? = null
    val values = HashMap<Int, Any>()

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("enumId", id)
            if (keyType != null) {
                put("key_type", keyType?.name)
            }
            if (valueType != null) {
                put("value_type", valueType?.name)
            }
            if (defaultInt != 0) {
                put("default_int", defaultInt)
            }
            if (defaultString != null) {
                put("default_string", defaultString)
            }
            if (values.isNotEmpty()) {
                put("values", buildJsonObject {
                    values.forEach { (key, value) ->
                        put("$key", value.toString())
                    }
                })
            }
        }
    }
}