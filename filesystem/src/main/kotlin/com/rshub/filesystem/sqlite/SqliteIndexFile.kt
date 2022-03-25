package com.rshub.filesystem.sqlite

import com.rshub.filesystem.ReferenceTable
import org.tmatesoft.sqljet.core.table.SqlJetDb
import java.io.Closeable
import java.nio.file.Path

/**
 * Read-write accessor to SQLite files
 */
class SqliteIndexFile(path: Path) : Closeable, AutoCloseable {
    val table: ReferenceTable? = null

    private val connection: SqlJetDb

    init {
        connection = SqlJetDb.open(path.toFile(), false)
    }


    fun hasReferenceTable(): Boolean {
        return connection.runReadTransaction {
            val table = it.getTable("CACHE_INDEX")
            val curr = table.lookup(table.primaryKeyIndexName, 1)
            return@runReadTransaction curr != null
        } as Boolean
        /*connection.prepareStatement("SELECT `DATA` FROM `cache_index` WHERE `KEY` = 1;").executeQuery()
            .use { return it.next() }*/
    }

    override fun close() {
        connection.close()
    }

    fun getMaxArchive(): Int {
        return connection.runReadTransaction {
            val table = it.getTable("cache")
            val curr = table.open()
            var max = 0L
            do {
                val n = curr.getInteger("KEY")
                if (n > max) {
                    max = n
                }
            } while (curr.next())
            return@runReadTransaction max.toInt()
        } as Int
    }

    fun exists(id: Int): Boolean {
        return connection.runReadTransaction {
            val table = it.getTable("CACHE")
            val curr = table.lookup("KEY", id)
            return@runReadTransaction curr.next()
        } as Boolean
    }

    fun getRaw(id: Int): ByteArray? {
        return connection.runReadTransaction {
            val table = it.getTable("CACHE")
            val curr = table.lookup(table.primaryKeyIndexName, id)
            return@runReadTransaction curr.getBlobAsArray("DATA")
        } as ByteArray?
    }

    fun getRawTable(): ByteArray? {
        return connection.runReadTransaction {
            val table = it.getTable("CACHE_INDEX")
            val curr = table.lookup(table.primaryKeyIndexName, 1)
            return@runReadTransaction curr.getBlobAsArray("DATA")
        } as ByteArray?
    }

    private inline fun <T : AutoCloseable?, R> T.use(block: (T) -> R): R {
        try {
            return block(this)
        } catch (e: Throwable) {
            throw e
        } finally {
            close()
        }
    }
}