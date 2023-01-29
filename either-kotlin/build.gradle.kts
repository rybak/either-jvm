// SPDX-License-Identifier: MIT
plugins {
	kotlin("jvm") version "1.8.0"
	`java-library`
}

group = "dev.andrybak"
version = "0.2-SNAPSHOT"

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

dependencies {
	implementation(kotlin("stdlib"))

	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
