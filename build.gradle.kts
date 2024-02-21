plugins {
    id("io.github.kabanfriends.build.java") apply false
    id("io.papermc.paperweight.userdev") version "1.5.11" apply false
}

val pluginVersion: String by rootProject

allprojects {
    group = "io.github.kabanfriends"
    version = pluginVersion

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.github.kabanfriends.build.java")
}