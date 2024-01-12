import com.google.samples.apps.nowinandroid.kotlinOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinTargetHierarchy.SourceSetTree.Companion.main

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.tom.library)
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp").version("1.9.10-1.0.13")
    id("com.squareup.wire")
}

wire {
    kotlin {
        android = true
        javaInterop = true
    }
}

androidLib {
    namespace = "com.tommannson.familycooking"

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

//    sourceSets {
//
//        main.java.srcDirs += "$buildDir/generated/source/wire"
//    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.remote)

    implementation(libs.imagepicker)

    implementation(libs.google.textRecognition)
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.playServices)
    implementation(libs.androidx.datastore)
    implementation("com.squareup.wire:wire-runtime:4.9.3")
}