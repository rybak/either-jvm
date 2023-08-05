// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	`maven-publish`
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			if (components.any { x -> return@any x.name == "kotlin" }) {
				from(components.getByName("kotlin"))
			} else {
				from(components.getByName("java"))
			}
		}
	}
}
