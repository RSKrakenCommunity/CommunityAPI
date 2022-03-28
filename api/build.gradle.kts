dependencies {
    api("com.github.RSKraken:KrakenAPI:master-SNAPSHOT")
    api(project(":filesystem"))
    api(project(":definitions"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rshub.api"
            artifactId = "KrakenCommunityAPI"
            version = "1.0-SNAPSHOT"
            from(components["java"])
        }
    }
}