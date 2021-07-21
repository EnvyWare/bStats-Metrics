import org.gradle.api.publish.PublishingExtension

apply(from = "generate-metrics.gradle.kts")
apply(from = "increment-version.gradle.kts")

plugins {
    `java-library`
    `maven-publish`
    signing
}

allprojects {
    group = "org.bstats"
}