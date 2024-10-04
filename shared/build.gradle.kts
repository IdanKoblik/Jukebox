plugins {
    id("java")
}

group = parent!!.group
version = parent!!.version

repositories {
    mavenCentral()
}

dependencies {
    api("com.fasterxml.jackson.core:jackson-databind:${project.findProperty("jackson.annotations.version")}")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:${project.findProperty("jackson.annotations.version")}")
}

tasks.test {
    useJUnitPlatform()
}