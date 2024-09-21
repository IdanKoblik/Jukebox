plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.idankoblik"
version = "dev-SNAPSHOT"

dependencies {
    implementation(project.project(":standalone"))
    implementation(project.project(":spigot"))

    implementation("net.kyori:adventure-platform-bukkit:${findProperty("kyori.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}