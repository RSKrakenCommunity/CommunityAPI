package com.javatar.codegen.specs

import java.nio.file.Paths

object CodeGenerator {

    @JvmStatic
    fun main(args: Array<String>) {
        LocationSpec.buildBankLocationFile(Paths.get("api/src/main/kotlin"))
        VariableSpec.buildVariableFile(Paths.get("api/src/main/kotlin"))
    }

}