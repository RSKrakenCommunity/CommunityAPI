import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("kotlin-kapt")
}

dependencies {
    implementation("org.fxmisc.richtext:richtextfx:0.10.8")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.insert-koin:koin-core:3.2.0-beta-1")
    implementation("org.pf4j:pf4j:3.6.0")
    implementation(project(":api"))
    implementation(project(":stub:api"))
    kapt("org.pf4j:pf4j:3.6.0")

    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")

}

tasks.named("build") {
    finalizedBy(tasks.withType<ShadowJar>())
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("DeveloperPlugin")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Plugin-Class"] = "com.rshub.DeveloperPlugin"
        attributes["Plugin-Id"] = "Developer Plugin"
        attributes["Plugin-Provider"] = "Javatar"
        attributes["Plugin-Version"] = "0.0.1"
    }
    dependencies {
        exclude(project(":stub:api"))
        exclude(dependency("com.github.RSKraken:KrakenAPI:master-SNAPSHOT"))
        exclude(dependency("org.pf4j:pf4j:3.6.0"))
        exclude(dependency("org.slf4j:slf4j-api:1.7.25"))
    }
    finalizedBy("copy")
}

tasks.create("copy", Copy::class.java) {
    val file = File(System.getProperty("user.home")).resolve("kraken-plugins")
    if(file.exists()) {
        from(tasks.withType<ShadowJar>())
        into(file)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rshub.javafx"
            artifactId = "KrakenCommunityJavaFx"
            version = "1.0-SNAPSHOT"
            from(components["java"])
        }
    }
}