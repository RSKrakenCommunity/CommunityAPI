package com.rshub.api.variables

import com.rshub.api.variables.impl.VariableBit

enum class Variables(private val variable: Variable) : Variable by variable {

    LUMBRIDGE_LODESTONE(VariableBit(35));

}