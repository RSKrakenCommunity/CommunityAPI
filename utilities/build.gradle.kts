publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rshub.utilities"
            artifactId = "KrakenCommunityUtilities"
            version = "1.0-SNAPSHOT"
            from(components["java"])
        }
    }
}