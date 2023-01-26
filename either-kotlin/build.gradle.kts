// SPDX-License-Identifier: MIT
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    `java-library`
}

group = "dev.andrybak"
version = "0.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
