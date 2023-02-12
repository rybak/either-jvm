// SPDX-License-Identifier: MIT
plugins {
	// TODO extract common parts to kotlin-library-conventions
	id("either-jvm-build.java-library-conventions")
	id("either-jvm-build.license-check")
	kotlin("jvm") version "1.8.20"
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(19))
	}
}

/*
 * How to add either-kotlin.jar to your dependencies:
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
	implementation(kotlin("stdlib"))
	implementation(group = "dev.andrybak.either-jvm", name = "either-kotlin", version = "0.2-SNAPSHOT")
}
