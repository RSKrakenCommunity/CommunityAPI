dependencies {
    implementation(project(":kraken-api"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rshub.imgui"
            artifactId = "KrakenCommunityImGui"
            version = "1.0-SNAPSHOT"
            from(components["java"])
        }
    }
}