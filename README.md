<h1 align="center" style="display: flex; align-items: center; justify-content: center;">
    Jukebox
</h1>

<div align="center">
    <img alt="GitHub License" src="https://img.shields.io/github/license/IdanKoblik/Jukebox">
</div>

### About
Jukebox is a powerful Java library specifically designed for handling NBS (Note Block Studio) files, primarily for use in Minecraft. It offers seamless functionality for playing, encoding, and decoding music compositions, making it an essential tool for developers looking to enhance their Minecraft projects. With its intuitive API, Jukebox allows for easy integration, enabling rich audio experiences derived from NBS files. Whether you're creating custom maps, plugins, or interactive experiences, Jukebox provides the tools to manipulate and enjoy complex musical data efficiently.

<br>

>[!IMPORTANT]
> ### Requirements
> * Java 17
> * Minecraft 1.8 and above (For the minecraft modules)

<br>

### Installation
>[!NOTE]
> Follow the next steps to include Jukebox in your project

**Maven:**
```xml
<dependency>
    <groupId>com.gradleup.shadow</groupId>
    <artifactId>shadow-gradle-plugin</artifactId>
    <version>version</version>
</dependency>

<repositories>
    <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.github.idankoblik.jukebox</groupId>
        <artifactId>[module]</artifactId>
        <version>[version]</version>
    </dependency>
</dependencies>
```

**Gradle (Kotlin)**
```kotlin
plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "version"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.idankoblik.jukebox:[module]:[version]")
}
```

**Gradle (Groovy)**
```groovy
plugins {
    id 'java'
    id 'com.github.johnrengelman.shadow' version 'version'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.idankoblik.jukebox:[module]:[version]'
}
```
<hr>

![Alt](https://repobeats.axiom.co/api/embed/8b0b65ded30d61eb1b895d9de29cc0d150fe44f5.svg "Repobeats analytics image")

If you have any questions or need further assistance, feel free to reach out!