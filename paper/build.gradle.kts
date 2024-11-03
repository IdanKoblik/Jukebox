plugins {
    id("java")
}

group = parent!!.project.group
version = parent!!.project.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project.project(":adventure"))
    implementation(project.project(":shared"))
    testImplementation(testFixtures(project.project(":shared")))

    compileOnly("org.github.paperspigot:paperspigot-api:${findProperty("paperspigot.version")}")
    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:${project.findProperty("mock.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}