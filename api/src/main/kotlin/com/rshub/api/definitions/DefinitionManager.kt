package com.rshub.api.definitions

import com.rshub.definitions.Definition
import com.rshub.definitions.loaders.Loader
import com.rshub.definitions.maps.MapObject
import com.rshub.definitions.objects.ObjectDefinition
import com.rshub.filesystem.Filesystem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonArray
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.math.pow

class DefinitionManager<T : Definition>(
    val fs: Filesystem,
    val indexId: Int,
    val archiveId: Int,
    val sizeShift: Int,
    val loader: Loader<T>
) {

    private val definitions = mutableMapOf<Int, T>()

    fun put(definition: T) {
        definitions[definition.id] = definition
    }

    fun remove(definition: T) {
        definitions.remove(definition.id)
    }

    fun cacheAll() {
        all()
    }

    fun all(filter: T.() -> Boolean = { true }): List<T> {
        val defs = mutableListOf<T>()
        repeat(getFileCount()) {
            if(definitions.containsKey(it)) {
                val def = definitions[it]!!
                if (filter.invoke(def)) {
                    defs.add(def)
                }
            } else {
                val def = get(it, true)
                if(filter.invoke(def)) {
                    defs.add(def)
                }
            }
        }
        return defs
    }

    operator fun get(id: Int, reload: Boolean = false): T {
        if(definitions.containsKey(id) && !reload) return definitions[id]!!
        val rt = fs.getReferenceTable(indexId) ?: return loader.newDefinition(id)
        val a = if (archiveId == -1) {
            rt.loadArchive(archiveId(id))
        } else rt.loadArchive(archiveId)
        val file = if (a != null && archiveId == -1) {
            a.files[fileId(id)]
        } else if (a != null) {
            a.files[id]
        } else null
        if(file != null) {
            val def = loader.load(id, ByteBuffer.wrap(file.data))
            put(def)
            return def
        }
        return loader.newDefinition(id)
    }

    fun getFileCount(): Int {
        val table = fs.getReferenceTable(indexId) ?: return 0
        if (archiveId != -1) {
            val a = table.loadArchive(archiveId)
            if (a != null) {
                return a.files.size
            }
        } else {
            val maxArchiveId: Int = table.highestEntry() - 1
            val lastArchive = table.loadArchive(maxArchiveId) ?: return 0
            val files: Optional<Int> = lastArchive.files.keys.stream().reduce { _, second -> second }
            if (files.isPresent) {
                return maxArchiveId * 2.0.pow(sizeShift.toDouble()).toInt() + files.get()
            }
        }
        return 0
    }

    fun archiveId(id: Int): Int {
        return id shr sizeShift
    }

    fun fileId(id: Int): Int {
        return id and (1 shl sizeShift) - 1
    }

    fun dump() {
        val j = Json { prettyPrint = true }
        val ja = buildJsonArray {
            repeat(getFileCount()) {
                val def = get(it)
                add(def.toJsonObject())
            }
        }
        val jsonString = j.encodeToString(ja)
        val name = this::class.simpleName
        if (name != null) {
            val n = name.substring(0, name.indexOf("Loader"))
            Files.write(
                Paths.get("C:\\Users\\david\\IdeaProjects\\RuneScape3Botting\\rs-api\\dump\\${n}s.json"),
                jsonString.toByteArray()
            )
        }
        System.gc()
    }

    companion object {

        val MapObject.def: ObjectDefinition get() = CacheHelper.getObject(objectId)

    }
}