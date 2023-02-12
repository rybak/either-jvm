// SPDX-License-Identifier: MIT
plugins {
	`java-library`
}

java {
	toolchain {
		/*
		 * either-java8 is compatible with any version of Java after Java 8.
		 */
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

dependencies {
	implementation(project(":either-java8"))
}

tasks.withType<JavaCompile> {
	// for versions of `javac` that don't have https://openjdk.org/jeps/400
	options.encoding = "UTF-8"
}
