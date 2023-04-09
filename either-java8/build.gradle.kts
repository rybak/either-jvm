// SPDX-License-Identifier: MIT
plugins {
	`java-library`
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

tasks.withType<JavaCompile> {
	// for versions of `javac` that don't have https://openjdk.org/jeps/400
	options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}

publishing {
	publications {
		create<MavenPublication>("eitherJava8Jar") {
			from(components.getByName("java"))
		}
	}
}
