package com.rshub.filesystem

import com.rshub.filesystem.ext.getSmartInt
import java.nio.ByteBuffer

class ReferenceTable(val filesystem: Filesystem, val index: Int) {

    var version = 0
    var format = 7
    var mask = 0
    internal var archives = sortedMapOf<Int, Archive>()

    fun decode(buffer: ByteBuffer) {
        format = buffer.get().toInt()
        if (format !in 5..7)
            throw IllegalArgumentException("reference table format not in range 5..7")
        version = if (format >= 6) buffer.int else 0
        mask = buffer.get().toInt()

        val hasNames = mask and 0x1 != 0
        val hasWhirlpools = mask and 0x2 != 0
        val hasSizes = mask and 0x4 != 0
        val hasHashes = mask and 0x8 != 0

        val readFormatInt: () -> Int = if (format >= 7) {
            { buffer.getSmartInt() }
        } else {
            { buffer.short.toInt() and 0xffff }
        }

        val archiveIds = IntArray(readFormatInt())
        for (i in archiveIds.indices) {
            val archiveId = readFormatInt() + if (i == 0) 0 else archiveIds[i - 1]
            archiveIds[i] = archiveId
            archives[archiveId] = Archive(archiveId)
        }

        if (hasNames) {
            archiveIds.forEach {
                (archives[it] ?: throw IllegalStateException("invalid archive id $it")).name = buffer.int
            }
        }

        archiveIds.forEach {
            (archives[it] ?: throw IllegalStateException("invalid archive id $it")).crc = buffer.int
        }

        if (hasHashes) {
            archiveIds.forEach {
                (archives[it] ?: throw IllegalStateException("invalid archive id $it")).hash = buffer.int
            }
        }

        if (hasWhirlpools) {
            archiveIds.forEach {
                val whirlpool = ByteArray(64)
                buffer.get(whirlpool)

                (archives[it] ?: throw IllegalStateException("invalid archive id $it")).whirlpool = whirlpool
            }
        }

        if (hasSizes) {
            archiveIds.forEach {
                val archive = archives[it] ?: throw IllegalStateException("invalid archive id $it")

                archive.compressedSize = buffer.int
                archive.uncompressedSize = buffer.int
            }
        }

        archiveIds.forEach {
            (archives[it] ?: throw IllegalStateException("invalid archive id $it")).version = buffer.int
        }

        val archiveFileIds = Array(archives.size) { IntArray(readFormatInt()) }

        for (i in archiveIds.indices) {
            val archive = archives[archiveIds[i]]
                ?: throw IllegalStateException("invalid archive id ${archiveIds[i]}")
            val fileIds = archiveFileIds[i]
            var fileId = 0
            for (j in fileIds.indices) {
                fileId += readFormatInt()
                archive.files[fileId] = ArchiveFile(fileId)
                fileIds[j] = fileId
            }
        }

        if (hasNames) {
            for (i in archiveIds.indices) {
                val archive = archives[archiveIds[i]]
                    ?: throw IllegalStateException("invalid archive id ${archiveIds[i]}")
                val fileIds = archiveFileIds[i]
                for (j in fileIds.indices) {
                    archive.files[fileIds[j]]!!.name = buffer.int
                }
            }
        }
    }

    fun highestEntry(): Int = if (archives.isEmpty()) 0 else archives.lastKey() + 1

    fun archiveSize(): Int {
        return if (mask and 0x4 != 0) {
            var sum = 0
            for (value in archives.values)
                sum += value.uncompressedSize
            sum
        } else {
            var sum = 0
            for (key in archives.keys) {
                val data = filesystem.read(index, key) ?: throw NullPointerException("$index, $key")
                val container = Container.decode(data)
                sum += container.data.size
            }
            sum
        }
    }

    fun totalCompressedSize(): Long {
        var sum = 0L
        for (value in archives.values)
            sum += value.compressedSize
        return sum
    }

    fun loadArchive(id: Int): Archive? {
        val archive = archives[id] ?: return null
        if (archive.loaded) return archive

        val raw = filesystem.read(index, id) ?: return null
        val file = Container.decode(raw).data
        //archive.decode(ByteBuffer.wrap(file))
        archive.decodeSqlite(ByteBuffer.wrap(file))
        return archive
    }

}