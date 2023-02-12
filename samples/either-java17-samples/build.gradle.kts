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
		languageVersion.set(JavaLanguageVersion.of(19))
	}
}

tasks.withType<JavaCompile> {
	options.compilerArgs.addAll(arrayOf("-Xlint:preview", "--enable-preview"))
}

/*
 * How to add either-java17.jar to your dependencies:
 * 1. Declare a repository (https://docs.gradle.org/current/userguide/declaring_repositories.html)
 */
repositories {
	maven {
		name = "Sonatype Nexus Snapshots"
		url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
	}
}
/*
 * 2. Declare a dependency (https://docs.gradle.org/current/userguide/declaring_dependencies.html)
 *    You can use either the long form with named parameters:
 *        implementation(group = "dev.andrybak.either-jvm", name = "either-java17", version = "0.2-SNAPSHOT")
 *    or short form, with GAV coordinates encoded into a single string:
 *        implementation("dev.andrybak.either-jvm:either-java17:0.2-SNAPSHOT")
 */
dependencies {
	implementation(group = "dev.andrybak.either-jvm", name = "either-java17", version = "0.2-SNAPSHOT")
}
