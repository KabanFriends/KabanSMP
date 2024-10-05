plugins {
    //id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.github.goooler.shadow") version "8.1.8"
}

project.base.archivesName = "KabanSMP-Bukkit"

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")

    implementation(project(":core"))
    implementation("net.kyori:adventure-platform-bukkit:4.3.3")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.17.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        relocate("net.kyori.adventure", "io.github.kabanfriends.kabansmp.libs.net.kyori.adventure")
    }
}