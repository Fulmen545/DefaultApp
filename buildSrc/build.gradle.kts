plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
    implementation("com.android.tools.build:gradle:8.12.2")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:1.13.1.0")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.2.2")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.10-2.0.2")

}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}