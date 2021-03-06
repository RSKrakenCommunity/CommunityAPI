dependencies {
    implementation("com.github.RSKraken:KrakenAPI:master-SNAPSHOT")
    implementation(project(":filesystem"))
    implementation(project(":utilities"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rshub.definitions"
            artifactId = "KrakenCommunityDefinitions"
            version = "1.0-SNAPSHOT"
            from(components["java"])
        }
    }
}