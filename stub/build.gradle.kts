import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(project(":stub:api"))
}

tasks.named("build") {
    finalizedBy(tasks.withType<ShadowJar>())
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("PluginManager")
    dependencies {
        include(dependency("org.pf4j:pf4j:3.6.0"))
        include(project(":kraken-api"))
        include(dependency("org.slf4j:slf4j-api:1.7.25"))
        include(project(":stub:api"))
    }
    finalizedBy("copy")
}

tasks.create("copy", Copy::class.java) {
    val file = File("C:\\Users\\david\\OneDrive\\Documents\\Kraken")
    if (file.exists()) {
        from(tasks.withType<ShadowJar>())
        into("C:\\Users\\david\\OneDrive\\Documents\\Kraken")
    }
}


