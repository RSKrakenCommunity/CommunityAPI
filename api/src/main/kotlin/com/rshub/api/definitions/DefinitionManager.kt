package com.rshub.api.definitions

import com.rshub.definitions.Definition

interface DefinitionManager<T : Definition> {

    fun put(definition: T)
    fun remove(definition: T)
    operator fun get(id: Int): T

}