---
title: Installing Jukebox on your plugin
editLink: true
---

# Installation

To get started with the api, you can integrate it into your project using one of the following build tools. Choose the one that fits your setup.

## Gradle (Kotlin DSL)

If you're using Gradle with Kotlin DSL, add the following to your `build.gradle.kts` file:

```kotlin
plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven {
        name = "ApartiumNexus"
        url = uri("https://nexus.voigon.dev/repository/beta-releases/")
    }
}

dependencies {
    implementation("com.github.idankoblik:[module]:[version]")
}
```

## Gradle (Groovy)

For projects using the Groovy DSL, include the following in your `build.gradle` file:

```groovy
plugins {
    id 'java'
    id 'com.github.johnrengelman.shadow' version '8.1.1'
}

repositories {
    maven {
        name = 'ApartiumNexus'
        url = uri('https://nexus.voigon.dev/repository/beta-releases/')
    }
}

dependencies {
    implementation 'com.github.idankoblik:[module]:[version]'
}
```

## Maven

If you are using Maven, add the following dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>ApartiumNexus</id>
        <url>https://nexus.voigon.dev/repository/beta-releases/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.idankoblik</groupId>
        <artifactId>[module]</artifactId>
        <version>[version]</version>
    </dependency>
</dependencies>
```

Replace `[module]` and `[version]` with the actual module name and version of the api.

---

If you have any questions or need further assistance, feel free to reach out!
