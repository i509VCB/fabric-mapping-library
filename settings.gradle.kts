rootProject.name = "fabric-mapping-library"

// Core
include(":core")

listOf("asm", "lorenz", "tree", "util").forEach {
    include(":$it")
    project(":$it").projectDir = file("extra/$it")
}

// Formats
listOf("csrg", "enigma", "proguard", "srg", "tiny").forEach {
    include(":$it")
    project(":$it").projectDir = file("format/$it")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
