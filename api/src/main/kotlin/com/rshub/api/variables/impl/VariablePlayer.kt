package com.rshub.api.variables.impl

import com.rshub.api.variables.Variable
import kraken.plugin.api.Client

/**
 * Kraken prefers to this class as ConVars
 */
class VariablePlayer(override val id: Int) : Variable {

    override val value: Int
        get() = Client.getConVarById(id)?.valueInt ?: 0

}