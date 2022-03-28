dependencies {
    implementation("com.github.RSKraken:KrakenAPI:master-SNAPSHOT")
    implementation(project(":api"))
    implementation(project(":javafx"))
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if(it.isDirectory) it else zipTree(it) })
    finalizedBy("copy")
}

tasks.create("copy", Copy::class.java) {
    val file = File("C:\\Users\\david\\OneDrive\\Documents\\Kraken")
    if(file.exists()) {
        from(tasks.withType<Jar>())
        into("C:\\Users\\david\\OneDrive\\Documents\\Kraken")
    }
}


