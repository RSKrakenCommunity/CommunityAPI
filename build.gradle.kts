plugins {
    kotlin("jvm") version "1.6.10" apply false
    java
}
subprojects {
    group = "com.rshub"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

