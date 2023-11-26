// SPDX-License-Identifier: MIT

pluginManagement {
	@Suppress("UnstableApiUsage")
	includeBuild("gradle/plugins")
}

rootProject.name = "either-jvm"

include("either-java8")
include("either-java17")
include("either-kotlin")

subdir("samples") {
	subproject("either-java8-local-samples")
	subproject("either-java8-samples")
	subproject("either-java17-samples")
	subproject("either-kotlin-samples")
}

subdir("gson") {
	subproject("either-java8-gson")
	subproject("either-java17-gson")
}

class DirScope(private val dirPath: String) {
	fun subproject(projectName: String) {
		include(projectName)
		project(":$projectName").projectDir = file("$dirPath/$projectName")
	}
}

fun subdir(dirPath: String, dirConfiguration: DirScope.() -> Unit) = DirScope(dirPath).dirConfiguration()
