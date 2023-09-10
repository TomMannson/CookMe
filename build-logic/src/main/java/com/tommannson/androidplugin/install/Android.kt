package com.tommannson.androidplugin.install

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate

fun Project.installAndroidApp(
    minSdkVersion: Int = 26,
    sdkVersion: Int = 33,
    composeEnabled: Boolean = true
) {
    val androidLib = (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("android") as com.android.build.gradle.LibraryExtension

    with(androidLib) {
        compileSdk = sdkVersion

        defaultConfig {
            minSdk = minSdkVersion
            targetSdk = sdkVersion

//            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//            consumerProguardFiles += file("consumer-rules.pro")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        buildFeatures {
            compose = composeEnabled
        }

//        tasks.withType<KotlinCompiler>().all {
//
//        }
//
//            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
//            kotlinOptions.freeCompilerArgs += "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
//            kotlinOptions.freeCompilerArgs += "-Xuse-experimental=io.ktor.locations.KtorExperimentalLocationsAPI"
//        }
//        project.tasks.withType(KotlinCompile)

//        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure(KotlinJvmO)

//        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
//            compilerOptions { /*...*/ }
//        }

//        val compileKotlin: Kotlin

//        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", ){}
//        val asd: KotlinJvmOptions?
//        compileKotlin {
//
//        }

//        kotlinOptions {
//            jvmTarget = "1.8"
//        }
    }
}