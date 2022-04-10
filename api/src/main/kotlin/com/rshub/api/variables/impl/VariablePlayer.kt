package com.rshub.api.variables.impl

import com.rshub.api.variables.Variable
import kotlinx.serialization.Serializable
import kraken.plugin.api.Client

/**
 * Kraken prefers to this class as ConVars
 */
@Serializable
class VariablePlayer(override val id: Int) : Variable {

    override val value: Int
        get() = Client.getConVarById(id)?.valueInt ?: 0

}