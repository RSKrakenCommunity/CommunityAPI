package com.rshub.definitions.managers

import com.rshub.definitions.InventoryDefinition
import com.rshub.definitions.loaders.InventoryLoader
import com.rshub.filesystem.Filesystem

class InventoryManager(val fs: Filesystem) : DefinitionManager<InventoryDefinition> {

    private val definitions = mutableMapOf<Int, InventoryDefinition>()
    private val loader = InventoryLoader()

    override fun put(definition: InventoryDefinition) {
        definitions[definition.id] = definition
    }

    override fun remove(definition: InventoryDefinition) {
        definitions.remove(definition.id)
    }

    override fun get(id: Int): InventoryDefinition {
        fun cacheDef(def: InventoryDefinition) : InventoryDefinition {
            definitions[def.id] = def
            return def
        }
        if(definitions.containsKey(id)) return definitions[id]!!
        val def = InventoryDefinition(id)
        val archive = fs.getReferenceTable(2)?.loadArchive(5) ?: return cacheDef(def)
        if(id < archive.files.size) {
            val data = archive.files[id]?.data ?: return cacheDef(def)
            return cacheDef(loader.load(id, data))
        }
        return cacheDef(def)
    }
}