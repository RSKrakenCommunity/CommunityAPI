dependencies {
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("io.netty:netty-buffer:4.1.74.Final")
    implementation("com.github.jponge:lzma-java:1.3")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    api(project(":utilities"))
}