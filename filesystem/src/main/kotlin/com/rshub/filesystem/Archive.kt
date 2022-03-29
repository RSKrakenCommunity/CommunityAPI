package com.rshub.filesystem

import java.nio.ByteBuffer
import kotlin.experimental.and

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
    val files = sortedMapOf<Int, ArchiveFile>()

    fun decodeSqlite(buffer: ByteBuffer) {
        loaded = true
        if (files.size == 1) {
            for (file in files.values) file.data = buffer.array()
            return
        }

        val first = (buffer.get() and 0xff.toByte()).toInt()
        if (first != 1) {
            System.err.println("Invalid first byte (Expected 1): $first")
            return
        }

        val size = files.size
        val ids = files.keys.stream().mapToInt { obj: Int -> obj }.toArray()
        val offsets = IntArray(size + 1)
        for (i in 0 until size + 1) offsets[i] = buffer.int and 0xffffff

        for (i in ids.indices) {
            val data = ByteArray(offsets[i + 1] - offsets[i])
            buffer.get(data)
            files[ids[i]]!!.data = data
        }
    }

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