plugins {
    id("java")
}

group = parent!!.group
version = parent!!.version

repositories {

}

dependencies {
    compileOnly(project.project(":shared"))

    compileOnly("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
    compileOnly("org.spigotmc:spigot:${project.findProperty("spigot.version")}")

    testImplementation("net.kyori:adventure-platform-bukkit:${findProperty("kyori.bukkit.version")}")
    testImplementation("net.kyori:adventure-api:${project.findProperty("kyori.version")}")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:${project.findProperty("mock.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}