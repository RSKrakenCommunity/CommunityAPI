package com.rshub.filesystem.sqlite

import com.rshub.filesystem.Filesystem
import com.rshub.filesystem.ext.toFilesystemHash
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path

class SqliteFilesystem(path: Path) : Filesystem(path) {
    private val indices: Array<SqliteIndexFile?> = Array(255) { null }

    private fun openIndex(indexId: Int) {
        if(indexId < 0 || indexId > 255)
            return
        indices[indexId] = SqliteIndexFile(path.resolve("js5-$indexId.jcache")).also { file ->
            println("Loading index $indexId, contains data: ${file.hasReferenceTable()} with max archive: ${file.getMaxArchive()}")
        }
    }

    override fun exists(index: Int, archive: Int): Boolean {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")
        if(indices[index] == null)
            openIndex(index)
        return indices[index]?.exists(archive) ?: false
    }

    override fun read(index: Int, archive: Int): ByteBuffer? {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")
        if(indices[index] == null)
            openIndex(index)
        return ByteBuffer.wrap(indices[index]?.getRaw(archive) ?: return null)
    }

    override fun read(index: Int, name: String): ByteBuffer? {
        if(indices[index] == null)
            openIndex(index)
        val table = getReferenceTable(index) ?: return null
        val hash = name.toFilesystemHash()
        val id = (table.archives.entries.firstOrNull { it.value.name == hash } ?: return null).key
        return read(index, id)
    }

    override fun readReferenceTable(index: Int): ByteBuffer? {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")
        if(indices[index] == null)
            openIndex(index)
        return ByteBuffer.wrap(indices[index]?.getRawTable() ?: return null)
    }

    override fun numIndices(): Int = indices.count { it != null }
}