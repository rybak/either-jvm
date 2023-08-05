// SPDX-License-Identifier: MIT

plugins {
	`java-library`
}

repositories {
	mavenCentral()
}

tasks.withType<JavaCompile> {
	// for versions of `javac` that don't have https://openjdk.org/jeps/400
	options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}
