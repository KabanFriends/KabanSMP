plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation("it.unimi.dsi:fastutil:8.5.13")
    implementation("io.netty:netty-buffer:4.1.107.Final")
    implementation("redis.clients:jedis:5.1.1")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        exclude(
                "com/google/",
                "org/slf4j/",
                "org/json/",
                "io/netty/",
                "it/unimi/"
        )
        archiveClassifier = null
    }
}