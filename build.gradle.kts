@file:Suppress("DSL_SCOPE_VIOLATION")

import com.tommannson.convention.defaults.configureDefaults
import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
// plugins {
//    id ("com.android.application") version libs.versions.androidPlugin apply false
//    id ("com.android.library") version libs.versions.androidPlugin apply false
//    id ("org.jetbrains.kotlin.android") version libs.versions.kotlin apply false
// //    id ("com.google.dagger.hilt.android") version "2.42" apply false
// }

val detektVersion = "1.22.0"

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}

plugins {
    alias(libs.plugins.tom.platform) apply false
    alias(libs.plugins.tom.library) apply false
    alias(libs.plugins.tom.application) apply false
    alias(libs.plugins.tom.kotlin) apply false
    alias(libs.plugins.tom.defaults)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.jvm) apply false
//    id("org.jetbrains.kotlin.jvm") version "1.8.20" apply false
//    id("com.android.library") version "8.0.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

configureDefaults {
    android {
        minSdk = 26
        sdk = 33
        composeEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

detekt {
    parallel = true
    toolVersion = libs.versions.detekt.get()
}

tasks.withType<Detekt> { dependsOn(":detekt:assemble") }

//dependencies {
//    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
//    detektPlugins(project(":detekt"))
//}