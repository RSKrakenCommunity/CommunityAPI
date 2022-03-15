package com.rshub.filesystem.sqlite

import com.rshub.filesystem.Filesystem
import com.rshub.filesystem.ext.toFilesystemHash
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path

class SqliteFilesystem(path: Path) : Filesystem(path) {
    var indices: Array<SqliteIndexFile?>

    init {
        Class.forName("org.sqlite.JDBC")
        indices = Array(255) {
            if (Files.exists(path.resolve("js5-$it.jcache"))) {
                SqliteIndexFile(path.resolve("js5-$it.jcache")).also { file ->
                    println("Loading index $it, contains data: ${file.hasReferenceTable()} with max archive: ${file.getMaxArchive()}")
                }
            } else null
        }
    }

    override fun createIndex(id: Int) {
        if (id < indices.size) {
            throw IllegalArgumentException("index $id already exists (arr size = ${indices.size})")
        }
        if (id != indices.size) {
            throw IllegalArgumentException("create indices one by one (got $id, start at ${indices.size})")
        }

        val tmp = indices
        this.indices = Array(id + 1) {
            if (it != id)
                return@Array tmp[it]
            SqliteIndexFile(path.resolve("js5-$it.jcache"))
        }
    }

    override fun exists(index: Int, archive: Int): Boolean {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        return indices[index]?.exists(archive) ?: false
    }

    override fun read(index: Int, archive: Int): ByteBuffer? {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        return ByteBuffer.wrap(indices[index]?.getRaw(archive) ?: return null)
    }

    override fun read(index: Int, name: String): ByteBuffer? {
        val table = getReferenceTable(index) ?: return null
        val hash = name.toFilesystemHash()
        val id = (table.archives.entries.firstOrNull { it.value.name == hash } ?: return null).key
        return read(index, id)
    }

    override fun readReferenceTable(index: Int): ByteBuffer? {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        return ByteBuffer.wrap(indices[index]?.getRawTable() ?: return null)
    }

    override fun numIndices(): Int = indices.size
}