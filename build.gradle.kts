import nmcp.NmcpPlugin
import okhttp3.internal.userAgent

plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.nmcp").version("0.0.8")
    signing
}

val snapshot: Boolean = isSnapshot()
val isCi = System.getenv("GITHUB_ACTOR") != null

group = "io.github.idankoblik.jukebox"
version = figureVersion()

subprojects {
    apply<JavaLibraryPlugin>()
    apply<MavenPublishPlugin>()
    apply<NmcpPlugin>()
    apply<SigningPlugin>()

    publishing {
        repositories {
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
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                version = figureVersion()

                from(components["java"])

                pom {
                    name = "Jukebox"
                    description = "NBS file format music player and handler library"
                    url = "https://idankoblik.github.io/Jukebox/"

                    licenses {
                        license {
                            name = "MIT License"
                            url = "https://github.com/IdanKoblik/Jukebox/blob/master/LICENSE"
                        }
                    }

                    developers {
                        developer {
                            id = "IdanKoblik"
                            name = "Idan Koblik"
                            email = "idankob@gmail.com"
                        }
                    }

                    scm {
                        connection = "scm:git:git://github.com/IdanKoblik/Jukebox.git"
                        developerConnection = "scm:git:ssh://github.com:IdanKoblik/Jukebox.git"
                        url = "http://github.com/IdanKoblik/Jukebox"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "ApartiumNexus"
            url = uri("https://nexus.voigon.dev/repository/apartium/")
        }

        maven {
            name = "apartium-releases"
            url = uri("https://nexus.voigon.dev/repository/apartium-releases")
        }
    }

    dependencies {
        api("net.apartium.cocoa-beans:common:${findProperty("cocoabeans.version")}")
        compileOnly("org.jetbrains:annotations:${findProperty("jetbrains.version")}")
        compileOnly("com.fasterxml.jackson.core:jackson-annotations:${findProperty("jackson.annotations.version")}")

        testImplementation("net.apartium.cocoa-beans:common:${findProperty("cocoabeans.version")}")
        testImplementation(platform("org.junit:junit-bom:${findProperty("junit.version")}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    java {
        withSourcesJar()
        withJavadocJar()

        modularity.inferModulePath = true
    }

    signing {
        if (isCi) {
            val signingSecret: String = System.getenv("SIGNING_SECRET") ?: findProperty("signing.secret").toString()
            val signingPassword: String = System.getenv("SIGNING_PASSWORD") ?: findProperty("signing.password").toString()

            useInMemoryPgpKeys(signingSecret, signingPassword)
        } else
            useGpgCmd()


        sign(publishing.publications["maven"])
    }

}

if (!snapshot && isCi) {
    nmcp {
        publishAllProjectsProbablyBreakingProjectIsolation {
            username = System.getenv("OSSRH_USERNAME") ?: findProperty("ossrh.username").toString()
            password = System.getenv("OSSRH_PASSWORD") ?: findProperty("ossrh.password").toString()
            publicationType = "AUTOMATIC"
        }
    }
}

dependencies {}

tasks.test {
    useJUnitPlatform()
}

fun figureVersion(): String {
    return (if (System.getenv("VERSION") == null) "dev" else System.getenv("VERSION")) + (if (snapshot) "-SNAPSHOT" else "")
}

fun isSnapshot(): Boolean {
    return (if (System.getenv("GITHUB_EVENT_NAME") == null) true else System.getenv("GITHUB_EVENT_NAME") != "workflow_dispatch")
}

tasks.javadoc {
    (options as StandardJavadocDocletOptions).addBooleanOption("Werror", true)
}

java {
    withSourcesJar()
    withJavadocJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}