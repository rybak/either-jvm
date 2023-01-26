// SPDX-License-Identifier: MIT
group = "dev.andrybak"

subprojects {
	repositories {
		mavenCentral()
	}
}

tasks.wrapper {
	distributionType = Wrapper.DistributionType.ALL
}
