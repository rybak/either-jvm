// SPDX-License-Identifier: MIT
import java.time.Instant

plugins {
	id("either-jvm-build.java-library-conventions")
}

val check = tasks.named("check")
val checkSpdxLicenseIdentifiersTask = tasks.register("checkSpdxLicenseIdentifiers") {
	group = "verification"
	description = "Ensures that every Java and Kotlin source code file has the SPDX-License-Identifier"
	inputs.files(sourceSets.map { it.allSource })
	val licenseCheckMarkerFile: File = project.buildDir.resolve("reports/license-check-marker.txt")
	outputs.file(licenseCheckMarkerFile.absolutePath)
	doLast {
		val marker = "SPDX-License-Identifier"
		sourceSets.all {
			allSource.sourceDirectories.asFileTree.forEach {
				if (it.extension == "java" || it.extension == "kt") {
					val firstLine = it.bufferedReader().readLine()
					if (!firstLine.contains(marker)) {
						throw GradleException("Missing marker '${marker}' in file '$it'")
					}
					logger.info("${it.name} - OK")
				}
			}
		}
		licenseCheckMarkerFile.writeText(Instant.now().toString())
	}
}

check.configure {
	dependsOn(checkSpdxLicenseIdentifiersTask)
}
