// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath(libs.androidx.navigation.safeargs.gradleplugin)

        //        classpath(Dependencies.Plugins.gradleToolsPlugin)
//        classpath(Dependencies.Plugins.firebasePlugin)
//        classpath(Dependencies.Plugins.googleServicesPlugin)
//        classpath(Dependencies.Plugins.crashlyticsPlugin)
//        classpath(Dependencies.Plugins.fabricPlugin)
//        classpath(Dependencies.Plugins.kotlinPlugin)
    }
}



