package com.rshub.api.definitions

import com.rshub.definitions.Definition
import com.rshub.definitions.maps.WorldObject
import com.rshub.definitions.objects.ObjectDefinition

interface DefinitionManager<T : Definition> {

    fun put(definition: T)
    fun remove(definition: T)
    operator fun get(id: Int): T

    companion object {

        val WorldObject.def: ObjectDefinition get() = CacheHelper.getObject(objectId)

    }
}