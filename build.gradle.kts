/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("org.jlleitschuh.gradle.ktlint").version("8.2.0")

    // id("com.github.johnrengelman.shadow").version("5.1.0")
     id("com.google.cloud.tools.jib").version("1.6.0")
    id("java")

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.http4k:http4k-core:3.179.1")
    implementation("org.http4k:http4k-client-okhttp:3.179.1")
    implementation("org.http4k:http4k-server-jetty:3.179.1")
    implementation("org.http4k:http4k-client-apache:3.179.1")
    implementation("org.http4k:http4k-serverless-lambda:3.179.1")
    implementation("org.http4k:http4k-format-jackson:3.179.1")
    implementation("com.beust:klaxon:5.0.1")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.19")
    testImplementation("io.mockk:mockk:1.9.3.kotlin12")
}

application {
    // Define the main class for the application.
    mainClassName = "http4ktest.AppKt"
}
