plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val releaseWorkflow = "IdanKoblik/Jukebox/.github/workflows/release.yml"
val snapshot: Boolean = System.getenv("GITHUB_WORKFLOW_REF") == null || !(System.getenv("GITHUB_WORKFLOW_REF").startsWith(releaseWorkflow))
val isCi = System.getenv("GITHUB_ACTOR") != null

group = "com.github.idan.koblik"
version = (if (System.getenv("VERSION") == null) "dev" else System.getenv("VERSION")) + (if (snapshot) "-SNAPSHOT" else "")

allprojects {
    apply<JavaLibraryPlugin>()
    apply<MavenPublishPlugin>()

    publishing {
        repositories {
            if (isCi) {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/IdanKoblik/Jukebox")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }

            if (isCi || project.findProperty("apartium.nexus.username") != null) {
                if (snapshot) {
                    maven {
                        name = "ApartiumMaven"
                        url = uri("https://nexus.voigon.dev/repository/beta-snapshots")
                        credentials {
                            username = (System.getenv("APARTIUM_NEXUS_USERNAME")
                                ?: project.findProperty("apartium.nexus.username")).toString()
                            password = (System.getenv("APARTIUM_NEXUS_PASSWORD")
                                ?: project.findProperty("apartium.nexus.password")).toString()
                        }
                    }
                } else {
                    maven {
                        name = "ApartiumMaven"
                        url = uri("https://nexus.voigon.dev/repository/beta-releases")
                        credentials {
                            username = (System.getenv("APARTIUM_NEXUS_USERNAME")
                                ?: project.findProperty("apartium.nexus.username")).toString()
                            password = (System.getenv("APARTIUM_NEXUS_PASSWORD")
                                ?: project.findProperty("apartium.nexus.password")).toString()
                        }
                    }
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains:annotations:${findProperty("jetbrains.version")}")
        compileOnly("org.projectlombok:lombok:${findProperty("lombok.version")}")
        annotationProcessor("org.projectlombok:lombok:${findProperty("lombok.version")}")

        testImplementation(platform("org.junit:junit-bom:${findProperty("junit.version")}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}

dependencies {}

tasks.test {
    useJUnitPlatform()
}