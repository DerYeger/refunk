plugins {
    `maven-publish`
    jacoco
    kotlin("jvm")
    //id("org.jlleitschuh.gradle.ktlint")
}

val junitVersion: String by project

val groupName = "eu.yeger"
val libraryName = "refunk"
val libraryVersion = "4.0.1"

group = groupName
version = libraryVersion

kotlin {
    explicitApi()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupName
            artifactId = libraryName
            version = libraryVersion

            from(components["java"])
        }
    }
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
