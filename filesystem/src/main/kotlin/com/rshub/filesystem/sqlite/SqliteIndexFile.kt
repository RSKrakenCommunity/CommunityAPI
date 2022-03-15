package com.rshub.filesystem.sqlite

import com.rshub.filesystem.ReferenceTable
import java.io.Closeable
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager

/**
 * Read-write accessor to SQLite files
 */
class SqliteIndexFile(path: Path) : Closeable, AutoCloseable {
    val table: ReferenceTable? = null

    private val connection: Connection

    init {
        connection = DriverManager.getConnection("jdbc:sqlite:$path")
        connection.prepareStatement(
            """
            CREATE TABLE IF NOT EXISTS `cache`(
              `KEY` INTEGER PRIMARY KEY,
              `DATA` BLOB,
              `VERSION` INTEGER,
              `CRC` INTEGER
            );
        """.trimIndent()
        ).use { stmt -> stmt.executeUpdate() }

        connection.prepareStatement(
            """
            CREATE TABLE IF NOT EXISTS `cache_index`(
              `KEY` INTEGER PRIMARY KEY,
              `DATA` BLOB,
              `VERSION` INTEGER,
              `CRC` INTEGER
            );
        """.trimIndent()
        ).use { stmt -> stmt.executeUpdate() }
    }


    fun hasReferenceTable(): Boolean {
        connection.prepareStatement("SELECT `DATA` FROM `cache_index` WHERE `KEY` = 1;").executeQuery()
            .use { return it.next() }
    }

    override fun close() {
        connection.prepareStatement("SELECT MAX(`KEY`) FROM `cache`;").close()
        connection.prepareStatement("SELECT `DATA` FROM `cache` WHERE `KEY` = ?;").close()
        connection.prepareStatement("SELECT `DATA` FROM `cache_index` WHERE `KEY` = 1;").close()
        connection.prepareStatement(
            """
                INSERT INTO `cache`(`KEY`, `DATA`, `VERSION`, `CRC`)
                  VALUES(?, ?, ?, ?)
                  ON CONFLICT(`KEY`) DO UPDATE SET
                    `DATA` = ?, `VERSION` = ?, `CRC` = ?
                  WHERE `KEY` = ?;
        """.trimIndent()
        ).close()
        connection.prepareStatement(
            """
                INSERT INTO `cache_index`(`KEY`, `DATA`, `VERSION`, `CRC`)
                  VALUES(1, ?, ?, ?)
                  ON CONFLICT(`KEY`) DO UPDATE SET
                    `DATA` = ?, `VERSION` = ?, `CRC` = ?
                  WHERE `KEY` = 1;
        """.trimIndent()
        ).close()
    }

    fun getMaxArchive(): Int {
        connection.prepareStatement("SELECT MAX(`KEY`) FROM `cache`;").executeQuery().use {
            if (!it.next()) return 0
            return it.getInt(1)
        }
    }

    fun exists(id: Int): Boolean {
        val stmt = connection.prepareStatement("SELECT 1 FROM `cache` WHERE `KEY` = ?;")
        stmt.clearParameters()
        stmt.setInt(1, id)
        stmt.executeQuery().use { return it.next() }
    }

    fun getRaw(id: Int): ByteArray? {
        connection.prepareStatement("SELECT `DATA` FROM `cache` WHERE `KEY` = ?;").use { stmt ->
            stmt.clearParameters()
            stmt.setInt(1, id)
            stmt.executeQuery().use {
                if (!it.next()) return null
                return it.getBytes("DATA")
            }
        }
    }

    fun getRawTable(): ByteArray? {
        connection.prepareStatement("SELECT `DATA` FROM `cache_index` WHERE `KEY` = 1;").executeQuery().use {
            if (!it.next()) return null
            return it.getBytes("DATA")
        }
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