plugins {
    id("io.papermc.paperweight.userdev")
}

val minecraftVersion: String by rootProject

project.base.archivesName = "KabanSMP-Injector"

repositories {
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    paperweight.paperDevBundle(minecraftVersion)

    compileOnly("space.vectrix.ignite:ignite-api:1.0.0")
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")
}

tasks {
    reobfJar {
        remapperArgs.add("--mixin")
    }

    build {
        dependsOn(reobfJar)
    }
}