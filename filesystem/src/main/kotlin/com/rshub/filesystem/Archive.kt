package com.rshub.filesystem

import java.nio.ByteBuffer

class Archive(
    val id: Int,
    var name: Int = 0
) {
    var crc = 0
    var version = 0
    var whirlpool: ByteArray? = null
    var uncompressedSize = 0
    var compressedSize = 0
    var hash = 0

    internal var loaded = false
    internal var requiresUpdate = false
    var files = sortedMapOf<Int, ArchiveFile>()

    fun decode(buffer: ByteBuffer) {
        loaded = true
        val rawArray = buffer.array()
        if (files.size == 1) {
            files[files.firstKey()]!!.data = rawArray
            return
        }
        val chuckCount = buffer.get().toInt() and 0xff
        val headerLength = buffer.int
        val entryChunkSizes = Array(files.size) { IntArray(chuckCount) }
        var delta = headerLength
        for (chunkIndex in 0 until chuckCount) {
            for (entryIndex in 0 until files.size) {
                val prevDelta = delta
                delta = buffer.int
                val chunkSize = delta - prevDelta
                entryChunkSizes[entryIndex][chunkIndex] = chunkSize
            }
        }
        if (buffer.position() != headerLength) {
            error("Not all or too much header data was consumed while decoding entries. ${headerLength - buffer.position()} bytes remain.")
        }
        val entryData = Array(files.size) { byteArrayOf() }
        for (chunkIndex in 0 until chuckCount) {
            for (entryIndex in 0 until files.size) {
                val entrySize = entryChunkSizes[entryIndex][chunkIndex]
                val chunkData = ByteArray(entrySize)
                buffer.get(chunkData)
                if (chunkIndex == 0) {
                    entryData[entryIndex] = chunkData
                } else {
                    entryData[entryIndex] = byteArrayOf(*entryData[entryIndex], *chunkData)
                }
            }
        }
        for (index in entryData.indices) {
            val entryBytes = entryData[index]
            val af = files[index]
            if (af == null) {
                files[index] = ArchiveFile(index, entryBytes)
            } else {
                files[index]!!.data = entryBytes
            }
        }
    }
}