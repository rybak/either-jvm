// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	id("either-jvm-build.license-check")
	id("either-jvm-build.maven-publish-conventions")
}

version = "0.2-SNAPSHOT"
description = "Type Either for Java 8+"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
}
