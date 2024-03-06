@file:Suppress("DSL_SCOPE_VIOLATION")
import com.tommannson.convention.defaults.configureDefaults
import io.gitlab.arturbosch.detekt.Detekt

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath (libs.mannodermaus.androidJunit)
        classpath (libs.hilt.android.gradle.plugin)
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
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.square.wire) apply false


}

configureDefaults {
    android {
        minSdk = 26
        sdk = 34
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

// TODO TK restore when config for detect will be ready
//dependencies {
//    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
//    detektPlugins(project(":detekt"))
//}