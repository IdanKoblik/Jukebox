plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = parent!!.group
version = parent!!.version

repositories {

}

dependencies {
    implementation(project.project(":shared"))

    compileOnlyApi("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
    compileOnlyApi("org.spigotmc:spigot:${project.findProperty("spigot.version")}")

    testImplementation("net.kyori:adventure-platform-bukkit:${findProperty("kyori.bukkit.version")}")
    testImplementation("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:${project.findProperty("mock.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}