package com.rshub.utilities

import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.zip.CRC32

private val CHARSET = charset("windows-1252")

fun ByteBuffer.toByteArray(): ByteArray {
    val remaining = ByteArray(remaining())
    get(remaining)
    return remaining
}

fun ByteBuffer.getCrc32(until: Int = limit()): Int {
        val crc = CRC32()
        for (i in 0 until until) {
            crc.update(get(i).toInt())
        }
        return crc.value.toInt()
}

fun ByteBuffer.getWhirlpool(): ByteArray {
    val pos = position()
    val data = ByteArray(limit())
    get(data)
    position(pos)
    return Whirlpool.getHash(data, 0, data.size)
}

fun ByteBuffer.getSmartSizeVar() : Int {
    var value = 0
    var curr: Int = unsignedSmart
    while (curr == 32767) {
        curr = unsignedSmart
        value += 32767
    }
    value += curr
    return value
}

fun ByteBuffer.rsaEncrypt(modulus: BigInteger, exponent: BigInteger): ByteBuffer {
    val raw = ByteArray(limit())
    get(raw)

    return ByteBuffer.wrap(BigInteger(raw).modPow(exponent, modulus).toByteArray())
}

val ByteBuffer.unsignedByte: Int get() = get().toInt() and 0xff
val ByteBuffer.unsignedShort: Int get() = short.toInt() and 0xffff

val ByteBuffer.string: String get() {
    val origPos = position()
    var length = 0
    while (get() != 0.toByte()) length++
    if (length == 0) return ""
    val byteArray = ByteArray(length)
    position(origPos)
    get(byteArray)
    position(position() + 1)
    return String(byteArray, CHARSET)
}

fun ByteBuffer.getSmallSmartInt(): Int {
    if (get(position()).toInt() and 0xff < 128)
        return get().toInt() and 0xff
    return (short.toInt() and 0xffff) - 0x8000
}

fun ByteBuffer.putSmallSmartInt(value: Int) {
    when (value) {
        in 0..127 -> put(value.toByte())
        in 0..32767 -> putShort((value + 32768).toShort())
        else -> throw IllegalArgumentException("Value cannot be greater than 32767")
    }
}

fun ByteBuffer.skip(bytes: Int) {
    position(position() + bytes)
}

val ByteBuffer.unsignedSmart: Int get() {
    var i: Int = unsignedByte
    if (i >= 0x80) {
        i -= 0x80
        return i shl 8 or unsignedByte
    }
    return i
}

fun ByteBuffer.getSmartInt(): Int {
    if (get(position()).toInt() < 0) {
        return int and 0x7fffffff
    }
    return short.toInt() and 0xffff
}

fun ByteBuffer.putSmartInt(value: Int) {
    if (value >= Short.MAX_VALUE) {
        putInt(value - Int.MAX_VALUE - 1)
    } else {
        putShort(if (value >= 0) value.toShort() else Short.MAX_VALUE)
    }
}

fun ByteBuffer.getMedium(): Int {
    return (((get().toInt() and 0xff) shl 16) or ((get().toInt() and 0xff) shl 8) or (get().toInt() and 0xff))
}

fun ByteBuffer.putMedium(value: Int) {
    put((value shr 16).toByte())
    put((value shr 8).toByte())
    put(value.toByte())
}

val ByteBuffer.decrsmart: Int get() {
    var first: Int = unsignedByte
    if (first < 128) {
        first -= 1
    } else {
        first = first shl 8 or unsignedByte
        first -= 0x8000
        first -= 1
    }
    return first
}

val ByteBuffer.masked: Map<Int, Int> get() {
    val result: MutableMap<Int, Int> = HashMap()
    var mask = unsignedByte
    while (mask > 0) {
        if (mask and 0x1 == 1) {
            result[getSmartInt()] = decrsmart
        } else {
            result[0] = 0
        }
        mask /= 2
    }
    return result
}