// SPDX-License-Identifier: MIT
plugins {
	id("either-jvm-build.java-library-conventions")
	`maven-publish`
	signing
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			if (components.any { x -> return@any x.name == "kotlin" }) {
				from(components.getByName("kotlin"))
			} else {
				from(components.getByName("java"))
			}
			pom {
				name.set("Either")
				description.set(project.description)
				val githubHttpUrl = "https://github.com/rybak/either-jvm"
				url.set(githubHttpUrl)
				licenses {
					license {
						name.set("MIT")
						url.set("https://opensource.org/license/mit/")
					}
				}
				scm {
					val githubScmUrl = "scm:git:git://github.com/rybak/either-jvm.git"
					connection.set(githubScmUrl)
					developerConnection.set(githubScmUrl)
					url.set(githubHttpUrl)
				}
				developers {
					developer {
						id.set("andrybak")
						name.set("Andrei Rybak")
						email.set("rybak.a.v+either@gmail.com")
					}
				}
			}
		}
	}
}

val isSnapshot = project.version.toString().contains("SNAPSHOT")

signing {
	useGpgCmd()
	sign(publishing.publications)
	isRequired = !isSnapshot
}
