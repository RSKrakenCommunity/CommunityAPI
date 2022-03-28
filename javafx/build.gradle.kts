dependencies {
    api("no.tornado:tornadofx:1.7.20")
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