package com.rshub.definitions.loaders

import com.rshub.definitions.Definition
import java.nio.ByteBuffer

interface Loader<T : Definition> {

    fun load(id: Int, buffer: ByteBuffer) : T
    fun newDefinition(id: Int): T

}