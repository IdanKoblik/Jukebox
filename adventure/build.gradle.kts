plugins {
    id("java")
}

group = parent!!.project.group
version = parent!!.project.version

repositories {

}

dependencies {
    implementation(project.project(":shared"))

    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
}

tasks.test {
    useJUnitPlatform()
}