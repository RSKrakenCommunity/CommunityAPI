package com.rshub.api.definitions

import com.rshub.definitions.loaders.ObjectLoader
import com.rshub.definitions.objects.ObjectDefinition
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class ObjectManager(val fs: Filesystem) : DefinitionManager<ObjectDefinition> {

    private val definitions = mutableMapOf<Int, ObjectDefinition>()
    private val loader = ObjectLoader()

    override fun put(definition: ObjectDefinition) {
        definitions[definition.id] = definition
    }

    override fun remove(definition: ObjectDefinition) {
        definitions.remove(definition.id)
    }

    override fun get(id: Int): ObjectDefinition {
        fun cacheDef(def: ObjectDefinition): ObjectDefinition {
            put(def)
            return def
        }
        if (definitions.containsKey(id)) return definitions[id]!!
        val archiveId = id shr 8
        val fileId = id and (1 shl 8) - 1
        val objConfigs = fs.getReferenceTable(16)
        if (objConfigs != null) {
            val archive = objConfigs.loadArchive(archiveId)
            if(archive != null) {
                val config = archive.files[fileId]
                val def = loader.load(id, ByteBuffer.wrap(config?.data ?: byteArrayOf(0)))
                return cacheDef(def)
            }
        }
        return cacheDef(ObjectDefinition(id))
    }
}