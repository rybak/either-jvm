// SPDX-License-Identifier: MIT

plugins {
	`java-library`
}

group = "dev.andrybak.either-jvm"

repositories {
	mavenCentral()
}

java {
	withJavadocJar()
	withSourcesJar()
}

tasks.withType<JavaCompile> {
	// for versions of `javac` that don't have https://openjdk.org/jeps/400
	options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}

tasks.withType<Javadoc> {
	options {
		encoding = "UTF-8"
		locale = "en"
		(this as StandardJavadocDocletOptions).tags?.add("implNote")
	}
}
