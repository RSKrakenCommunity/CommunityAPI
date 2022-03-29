dependencies {
    api("no.tornado:tornadofx:1.7.20")
    api("io.insert-koin:koin-core:3.2.0-beta-1")
    implementation(project(":api"))
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