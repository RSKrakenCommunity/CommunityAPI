package com.rshub.json.serialization

import com.rshub.javafx.ui.model.VariableEditModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

object VariableEditModelSerializer : KSerializer<VariableEditModel> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Variable") {
        element<String>("variable_name")
        element<Int>("variable_id")
        element<String>("variable_type")
    }

    override fun deserialize(decoder: Decoder): VariableEditModel {
        decoder.decodeStructure(LocationModelSerializer.descriptor) {
            var name = ""
            var type = ""
            var id = -1
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, index)
                    1 -> id = decodeIntElement(descriptor, index)
                    2 -> type = decodeStringElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            return VariableEditModel(name, id, VariableEditModel.Type.valueOf(type))
        }
    }

    override fun serialize(encoder: Encoder, value: VariableEditModel) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.variableName.get())
            encodeIntElement(descriptor, 1, value.variableId.get())
            encodeStringElement(descriptor, 2, value.variableType.get().name)
        }
    }
}