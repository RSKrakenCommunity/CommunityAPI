package com.rshub.utilities

import com.rshub.utilities.TextUtils.charToCp1252

fun String.toFilesystemHash(): Int {
    val size = length
    var char = 0
    for (index in 0 until size)
        char = (char shl 5) - char + charToCp1252(this[index])
    return char
}