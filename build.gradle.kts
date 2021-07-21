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

configure<PublishingExtension> {
    subprojects {
        val isReleaseVersion = !version.toString().endsWith("SNAPSHOT")

        apply(plugin = "java-library")
        apply(plugin = "maven-publish")
        apply(plugin = "signing")

        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    groupId = "org.bstats"
                    afterEvaluate {
                        artifactId = "bstats-${project.name}"
                    }
                    from(components["java"])
                    pom {
                        name.set("bStats-Metrics")
                        description.set("The bStats Metrics class")
                        url.set("https://bStats.org")
                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://opensource.org/licenses/mit-license.php")
                            }
                        }
                        scm {
                            connection.set("scm:git:https://github.com/Bastian/bStats-Metrics.git")
                            developerConnection.set("scm:git:git@github.com:Bastian/bStats-Metrics.git")
                            url.set("https://github.com/Bastian/bStats-Metrics")
                        }
                        developers {
                            developer {
                                id.set("Bastian")
                                name.set("Bastian Oppermann")
                                email.set("bastian@bstats.org")
                                url.set("https://github.com/Bastian")
                                timezone.set("Europe/Berlin")
                            }
                        }
                    }
                }
            }
        }
    }
}