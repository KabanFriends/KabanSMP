pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "KabanSMP"

includeBuild("build-logic")
includeBuild("third-party/Ascension")

include("core")
include("translation")
include("injector")
