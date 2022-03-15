package com.rshub.definitions.cs2

enum class BaseVarType(val clazz: Class<*>) {
    INTEGER(Int::class.java),
    LONG(Long::class.java),
    STRING(String::class.java),
    COORDFINE(Int::class.java);

    companion object {
        fun forId(id: Int): BaseVarType {
            return values()[id]
        }
    }
}