publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "kraken.plugin.api"
            artifactId = "KrakenAPI"
            version = "1.1-SNAPSHOT"
            from(components["java"])
        }
    }
}