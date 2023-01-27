// SPDX-License-Identifier: MIT

subprojects {
	group = "dev.andrybak.either-jvm"

	repositories {
		mavenCentral()
	}
}

tasks.wrapper {
	distributionType = Wrapper.DistributionType.ALL
}
