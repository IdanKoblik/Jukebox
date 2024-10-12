plugins {
    id("java")
}

group = parent!!.group
version = parent!!.version

repositories {

}

dependencies {
    implementation(project.project(":shared"))

    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
}

tasks.test {
    useJUnitPlatform()
}