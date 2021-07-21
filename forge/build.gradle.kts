import net.minecraftforge.gradle.userdev.UserDevExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.support.classPathBytesRepositoryFor

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://maven.minecraftforge.net/")
        maven(url = "https://files.minecraftforge.net/maven")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:4.1.+") {
            isChanging = true
        }
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath("com.github.jengelman.gradle.plugins:shadow:2.0.2")
    }
}

plugins {
    java
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

apply {
    plugin("net.minecraftforge.gradle")
}

configure<UserDevExtension> {
    mappings("snapshot", "20180814-1.12")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.spongepowered.org/maven")
    }
}

dependencies {
    "minecraft"("net.minecraftforge:forge:1.12.2-14.23.5.2854")
    "shadow"("org.spongepowered:configurate-hocon:4.0.0")
    "shadow"(project(":base"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}


tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("forge")
        configurations.clear()
        configurations.add(project.configurations.shadow.get())
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "org.bstats.forge.Metrics"))
            attributes(mapOf("classpath" to sourceSets.main.get().compileClasspath))
        }

        relocate("org.spongepowered", "org.bstats.config")
        relocate("io.leangen", "org.bstats.config")
        relocate("com.typesafe", "org.bstats.config")
    }

    build {
        dependsOn(shadowJar)
    }
}
