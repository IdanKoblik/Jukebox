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

    compileOnly("org.spigotmc:spigot:${project.findProperty("spigot.version")}")

    testImplementation("org.mockito:mockito-core:5.14.1")
    testImplementation("net.kyori:adventure-platform-bukkit:${findProperty("kyori.bukkit.version")}")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:${project.findProperty("mock.bukkit.version")}")
}

tasks.test {
    useJUnitPlatform()
}