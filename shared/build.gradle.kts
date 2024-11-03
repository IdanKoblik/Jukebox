plugins {
    id("java")
    id("java-test-fixtures")
}

group = parent!!.project.group
version = parent!!.project.version

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.fasterxml.jackson.core:jackson-databind:${project.findProperty("jackson.annotations.version")}")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:${project.findProperty("jackson.annotations.version")}")
}

tasks.test {
    useJUnitPlatform()
}