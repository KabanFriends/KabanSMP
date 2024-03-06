pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "KabanSMP"

includeBuild("build-logic")

include("core")
include("translation")
include("networking")
include("injector")
include("velocity")
