plugins {
    id("io.papermc.paperweight.userdev")
}

val subprojectsToBundle = arrayOf("translation", "networking")

val minecraftVersion: String by rootProject
val floodgateVersion: String by rootProject
val vaultVersion: String by rootProject
val protocolLibVersion: String by rootProject

project.base.archivesName = "KabanSMP"

repositories {
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    paperweight.paperDevBundle(minecraftVersion)

    implementation(project(":translation"))
    implementation(project(":injector"))
    implementation(project(":networking"))
    implementation("org.geysermc.floodgate:api:${floodgateVersion}")
    implementation("com.github.MilkBowl:VaultAPI:${vaultVersion}") {
        exclude("org.bukkit")
    }
    implementation("com.comphenix.protocol:ProtocolLib:${protocolLibVersion}")
}

tasks {
    compileJava {
        dependsOn(":networking:build")
    }

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