// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	`maven-publish`
}

version = "0.2-SNAPSHOT"

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

publishing {
	publications {
		create<MavenPublication>("eitherJava8Jar") {
			from(components.getByName("java"))
		}
	}
}
