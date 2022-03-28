plugins {
    kotlin("jvm") version "1.6.10" apply false
    java
    `maven-publish`
}

group = "com.rshub"
version = "1.0-SNAPSHOT"

subprojects {
    group = "com.rshub"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("kotlin")
        plugin("maven-publish")
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.tmatesoft.com/content/repositories/releases")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.rshub"
                artifactId = "KrakenCommunityAPI"
                version = "1.0-SNAPSHOT"
                from(components["java"])
            }
        }
    }
}
