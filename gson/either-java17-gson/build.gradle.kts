// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	id("either-jvm-build.license-check")
}

java {
	toolchain {
		/*
		 * either-java17 is compatible with any version of Java after Java 17.
		 */
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

dependencies {
	api(project(":either-java17"))
	// TODO: use version range for flexibility in clients
	api("com.google.code.gson:gson:2.10.1")
	testImplementation(platform(libs.junitBom))
	testImplementation(libs.junitApi)
}
