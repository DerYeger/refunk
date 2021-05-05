pluginManagement {
    val kotlinVersion: String by settings
    val ktlintVersion: String by settings

    plugins {
        `maven-publish`
        jacoco
        kotlin("jvm") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    }
}

rootProject.name = "refunk"
