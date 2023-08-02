// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	id("either-jvm-build.license-check")
	kotlin("jvm") version "1.8.20"
	id("org.jetbrains.dokka") version "1.8.20"
	`maven-publish`
}

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

publishing {
	publications {
		create<MavenPublication>("eitherKotlinJar") {
			from(components.getByName("kotlin"))
		}
	}
}
