// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

buildscript {
    dependencies {
        val nav_version = "2.7.7" // ou a versão que você estiver usando
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}