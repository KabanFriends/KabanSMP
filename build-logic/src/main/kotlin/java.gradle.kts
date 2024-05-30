package io.github.kabanfriends.build

plugins {
    java
}

tasks {
    compileJava {
        options.release.set(21)
        options.encoding = "UTF-8"
    }
}