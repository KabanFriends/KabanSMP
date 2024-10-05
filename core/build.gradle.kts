plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev")
}

val subprojectsToBundle = arrayOf("translation")

val minecraftVersion: String by rootProject

project.base.archivesName = "KabanSMP"

repositories {
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    paperweight.paperDevBundle(minecraftVersion)

    compileOnly(project(":injector"))

    compileOnly("net.kyori:adventure-platform-bukkit:4.3.3") { isTransitive = false }
    compileOnly("org.geysermc.floodgate:api:2.2.3-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") { exclude("org.bukkit") }
    compileOnly("com.github.retrooper.packetevents:spigot:2.3.0")
    compileOnly("com.discordsrv:api:3.0.0-SNAPSHOT")

    implementation(project(":translation")) { isTransitive = false }
    implementation("net.dv8tion:JDA:5.0.0-beta.24") { exclude("opus-java") }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        rootProject.subprojects.forEach { subproject ->
            if (!subprojectsToBundle.contains(subproject.name)) return@forEach
            val platformSourcesJarTask = subproject.tasks.findByName("jar") as? Jar ?: return@forEach
            dependsOn(platformSourcesJarTask)
            from(zipTree(platformSourcesJarTask.archiveFile))
        }

        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }
}