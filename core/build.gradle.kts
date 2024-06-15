plugins {
    id("io.papermc.paperweight.userdev")
}

val subprojectsToBundle = arrayOf("translation")

val minecraftVersion: String by rootProject

project.base.archivesName = "KabanSMP"

repositories {
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    paperweight.paperDevBundle(minecraftVersion)

    implementation(project(":translation"))
    implementation(project(":injector"))
    implementation("org.geysermc.floodgate:api:2.2.3-SNAPSHOT")
    implementation("com.github.MilkBowl:VaultAPI:1.7") {
        exclude("org.bukkit")
    }
    implementation("com.comphenix.protocol:ProtocolLib:5.2.0-SNAPSHOT")
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        rootProject.subprojects.forEach { subproject ->
            if (!subprojectsToBundle.contains(subproject.name)) return@forEach
            val platformSourcesJarTask = subproject.tasks.findByName("jar") as? Jar ?: return@forEach
            dependsOn(platformSourcesJarTask)
            from(zipTree(platformSourcesJarTask.archiveFile))
        }
    }
}