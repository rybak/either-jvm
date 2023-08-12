// SPDX-License-Identifier: MIT
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("either-jvm-build.java-library-conventions")
	id("either-jvm-build.license-check")
	kotlin("jvm") version "1.8.20"
	id("org.jetbrains.dokka") version "1.8.20"
	id("either-jvm-build.maven-publish-conventions")
}

version = "0.3-SNAPSHOT"
description = "Type Either for Kotlin"

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions {
		apiVersion = "1.8"
		languageVersion = "1.8"
	}
}

dependencies {
	testImplementation(libs.kotlinTestJunit)
	testImplementation(platform(libs.junitBom))
	testImplementation(libs.junitJupiter)
}
