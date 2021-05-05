plugins {
    `maven-publish`
    jacoco
    kotlin("jvm")
    //id("org.jlleitschuh.gradle.ktlint")
}

val junitVersion: String by project

group = "eu.yeger"
version = "4.0.0"

kotlin {
    explicitApi()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        useJUnitPlatform()
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
    }
}
