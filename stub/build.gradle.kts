dependencies {
    implementation("com.github.RSKraken:KrakenAPI:master-SNAPSHOT")
    implementation(project(":api"))
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if(it.isDirectory) it else zipTree(it) })
    finalizedBy("copy")
}

tasks.create("copy", Copy::class.java) {
    from(tasks.withType<Jar>())
    into("C:\\Users\\david\\OneDrive\\Documents\\Kraken")
}


