plugins {
    id("java")
}

group = parent!!.group
version = parent!!.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project.project(":adventure"))
    implementation(project.project(":shared"))

    compileOnly("org.github.paperspigot:paperspigot-api:${findProperty("paperversion.version")}")
    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:${project.findProperty("mock.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}