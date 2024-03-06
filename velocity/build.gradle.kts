plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

project.base.archivesName = "KabanSMP-Velocity"

val subprojectsToBundle = arrayOf("translation", "networking")

repositories {
    maven("https://repo.opencollab.dev/main/")
    maven("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
}

dependencies {
    implementation("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")

    implementation("dev.simplix:protocolize-api:2.3.3")
    implementation("org.geysermc.geyser:api:2.2.1-SNAPSHOT")
    implementation("org.geysermc.floodgate:api:2.2.1-SNAPSHOT")
    implementation("io.netty:netty-buffer:4.1.107.Final")

    shadow(project(":translation"))
    shadow(project(":networking"))
    shadow("commons-io:commons-io:2.15.1")
    shadow("commons-lang:commons-lang:2.6")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    compileJava {
        dependsOn(":networking:build")
    }

    shadowJar {
        configurations = listOf(project.configurations.shadow.get())
        archiveClassifier = null

        exclude(
                "com/google/",
                "org/slf4j/",
                "org/json/",
                "org/intellij/",
                "org/jetbrains/",
                "net/kyori/",
                "io/netty/",
                "it/unimi/"
        )
    }
}