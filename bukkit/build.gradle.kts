plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

project.base.archivesName = "KabanSMP-Bukkit"

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")

    implementation(project(":core"))
    implementation("net.kyori:adventure-platform-bukkit:4.3.3")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}