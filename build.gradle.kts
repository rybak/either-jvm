// SPDX-License-Identifier: MIT
plugins {
	id("io.github.gradle-nexus.publish-plugin") version ("2.0.0-rc-1")
}

// placeholder for nexusPublishing plugin -- needed to detect SNAPSHOT vs not snapshot
version = "0.1-SNAPSHOT"

nexusPublishing {
	packageGroup.set("dev.andrybak.either-jvm")
	repositories {
		sonatype { // only for users registered in Sonatype after 24 Feb 2021
			nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
			snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
		}
	}
}

tasks.wrapper {
	distributionType = Wrapper.DistributionType.ALL
}
