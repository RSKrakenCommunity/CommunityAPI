package com.rshub.api.variables.impl

import com.rshub.api.definitions.CacheHelper
import com.rshub.api.variables.Variable
import kotlinx.serialization.Serializable
import kraken.plugin.api.Client

@Serializable
class VariableBit(override val id: Int) : Variable {

    val def get() = CacheHelper.getVarbit(id)
    override val value: Int
        get() {
            val convar = Client.getConVarById(def.index) ?: return 0
            val lsb = def.lsb
            val msb = def.msb
            val mask = (1 shl msb - lsb + 1) - 1
            return convar.valueInt shr lsb and mask
        }

}