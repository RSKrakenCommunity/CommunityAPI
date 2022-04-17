package com.rshub.api.variables

import com.rshub.api.banking.Bank
import com.rshub.api.containers.Container
import com.rshub.api.containers.InventoryHelper
import kotlin.reflect.KProperty

interface Variable {

    val id: Int
    val value: Int

    companion object {
        inline fun <reified T> Variables.getValue(ref: Any?, prop: KProperty<*>) : T {
            return when(T::class) {
                Container::class -> InventoryHelper.getInventory(value) as T
                Boolean::class -> (value == 1) as T
                else -> value as T
            }
        }
    }
}