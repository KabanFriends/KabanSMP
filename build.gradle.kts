plugins {
    id("io.github.kabanfriends.build.java") apply false
    id("io.papermc.paperweight.userdev") version "1.7.1" apply false
}

val pluginVersion: String by rootProject

allprojects {
    group = "io.github.kabanfriends"
    version = pluginVersion

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

subprojects {
    apply(plugin = "io.github.kabanfriends.build.java")
}