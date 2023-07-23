// SPDX-License-Identifier: MIT

pluginManagement {
	@Suppress("UnstableApiUsage")
	includeBuild("gradle/plugins")
}

rootProject.name = "either-jvm"

include("either-java8")
include("either-java17")
include("either-kotlin")
