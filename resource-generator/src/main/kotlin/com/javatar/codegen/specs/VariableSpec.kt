package com.javatar.codegen.specs

import com.rshub.api.variables.Variable
import com.rshub.api.variables.impl.VariableBit
import com.rshub.api.variables.impl.VariablePlayer
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import java.nio.file.Path
import kotlin.reflect.KProperty

object VariableSpec {

    fun buildVariableFile(path: Path) {
        val fileSpec = FileSpec.builder("com.rshub.api.variables", "Variables")
        fileSpec.addFileComment("Generated by kraken community, do not modify!")

        val param = ParameterSpec.builder("variable", Variable::class)
            .build()

        val enum = TypeSpec.enumBuilder("Variables")
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(param)
                    .build()
            )
            .addModifiers(KModifier.ENUM)

        enum.superinterfaces[Variable::class.asClassName()] = CodeBlock.of(param.name)

        val list = loadVariables()
        for (vr in list) {
            val enumName = vr.variableName.uppercase().replace(' ', '_')
            val variableId = vr.variableId
            val klass = when (vr.type) {
                "VARBIT" -> VariableBit::class
                else -> VariablePlayer::class
            }
            enum.addEnumConstant(
                enumName,
                TypeSpec
                    .anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%T($variableId)", klass).build()
            )
        }

        fileSpec.addType(enum.addFunction(addGetValueFunc()).build())
            .build().writeTo(path)
    }

    private fun addGetValueFunc(): FunSpec {
        val refSpec = ParameterSpec.builder("ref", Any::class.asTypeName().copy(nullable = true)).build()
        val kpropSpec = ParameterSpec.builder(
            "prop",
            KProperty::class.asTypeName()
                .parameterizedBy(STAR)
        ).build()
        return FunSpec.builder("getValue")
            .addModifiers(KModifier.OPERATOR)
            .addParameter(refSpec)
            .addParameter(kpropSpec)
            .addStatement("return value")
            .returns(Int::class)
            .build()
    }

    private fun loadVariables(): List<Var> {
        val con = URL("https://rskrakencommunity.github.io/KrakenCommunityPages/variables.json")
        val stream = String(con.openStream().readBytes())
        return Json.decodeFromString(stream)
    }

    @Serializable
    data class Var(
        @SerialName("variable_name")
        val variableName: String,
        @SerialName("variable_id")
        val variableId: Int,
        @SerialName("variable_type")
        val type: String
    )

    @JvmStatic
    fun main(args: Array<String>) {

    }

}