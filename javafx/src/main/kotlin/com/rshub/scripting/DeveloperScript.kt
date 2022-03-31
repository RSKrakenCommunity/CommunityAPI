package com.rshub.scripting

import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    // File extension for the script type
    fileExtension = "scriptwithdeps.kts",
    // Compilation configuration for the script type
    compilationConfiguration = DeveloperScriptConfiguration::class
)
abstract class DeveloperScript