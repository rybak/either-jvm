// SPDX-License-Identifier: MIT
plugins {
	`java-library`
}

version = "0.2-SNAPSHOT"

java {
	toolchain {
		// TODO switch to version 17 proper after it will be released
		languageVersion.set(JavaLanguageVersion.of(16))
	}
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.compilerArgs.addAll(arrayOf("-Xlint:preview", "--enable-preview"))
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
	jvmArgs("--enable-preview")
}
