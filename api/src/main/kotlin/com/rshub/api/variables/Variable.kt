package com.rshub.api.variables

import kotlin.reflect.KProperty

interface Variable {

    val id: Int
    val value: Int

    companion object {
        operator fun getValue(any: Any, prop: KProperty<*>) : Int {
            val nspace = any::class.simpleName ?: return 0
            return VariableHelper.getVariable(nspace, prop.name)
        }
    }
}