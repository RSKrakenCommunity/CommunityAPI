package com.rshub.definitions

import com.rshub.definitions.cs2.ScriptVarType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class ParamDefinition(override val id: Int) : Definition {

    var defaultInt = 0
    var autoDisable = true
    var typeId = 0
    var defaultString: String? = null
    var type: ScriptVarType? = null

    override fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("paramId", id)
            put("auto_disable", autoDisable)
            put("typeId", typeId)
            if (type != null) {
                put("type", type?.name)
            }
            if (defaultInt != 0) {
                put("default_int", defaultInt)
            }
            if (defaultString != null) {
                put("default_string", defaultString)
            }
        }
    }
}