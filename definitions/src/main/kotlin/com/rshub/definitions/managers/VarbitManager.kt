package com.rshub.definitions.managers

import com.rshub.definitions.VarbitDefinition
import com.rshub.definitions.loaders.VarbitLoader
import com.rshub.filesystem.Filesystem
import java.nio.ByteBuffer

class VarbitManager(val fs: Filesystem) : DefinitionManager<VarbitDefinition> {

    private val definitions = mutableMapOf<Int, VarbitDefinition>()
    private val loader = VarbitLoader()

    override fun put(definition: VarbitDefinition) {
        definitions[definition.id] = definition
    }

    override fun remove(definition: VarbitDefinition) {
        definitions.remove(definition.id)
    }

    override fun get(id: Int): VarbitDefinition {
        fun cacheDef(def: VarbitDefinition): VarbitDefinition {
            definitions[def.id] = def
            return def
        }
        if (definitions.containsKey(id)) return definitions[id]!!
        val def = VarbitDefinition(id)
        val archive = fs.getReferenceTable(2)?.loadArchive(69) ?: return cacheDef(def)
        if (id < archive.files.size) {
            val data = archive.files[id]?.data ?: return cacheDef(def)
            return cacheDef(loader.load(id, ByteBuffer.wrap(data)))
        }
        return cacheDef(def)
    }
}