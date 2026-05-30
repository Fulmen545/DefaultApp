// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath(libs.kotlin.gradle.plugin)

    }
}



