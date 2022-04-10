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
            val bits = (def.msb - def.lsb)
            return convar.getValueMasked(def.lsb, if(bits <= 0) 1 else bits)
        }

}