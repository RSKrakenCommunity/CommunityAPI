package com.rshub.buffer.exts

fun <T : Any> Array<T?>.isJustNulls() : Boolean {
    return filterNotNull().none { it != "null" }
}