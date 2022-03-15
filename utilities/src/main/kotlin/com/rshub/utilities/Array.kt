package com.rshub.utilities

fun <T : Any> Array<T?>.isJustNulls() : Boolean {
    return filterNotNull().none { it != "null" }
}