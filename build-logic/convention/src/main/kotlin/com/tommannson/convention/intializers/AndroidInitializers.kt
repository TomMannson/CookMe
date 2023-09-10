package com.tommannson.convention.intializers

//import com.google.samples.apps.nowinandroid.configureFlavors
//import com.google.samples.apps.nowinandroid.configurePrintApksTask
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.google.samples.apps.nowinandroid.configureKotlinAndroid
import com.tommannson.convention.config.AndroidConfig
import com.tommannson.convention.ext.findVersionInt
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

//fun Project.androidLibExt(
//    androidConfig: AndroidConfig
//) {
//    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
//    with(pluginManager) {
//        apply("com.android.library")
//        apply("org.jetbrains.kotlin.android")
//    }
//
//    extensions.configure<LibraryExtension> {
//        configureKotlinAndroid(this)
////                defaultConfig.targetSdk = 33
//        compileSdk = libs.findVersionInt("android_targetSdk")
//
//        defaultConfig {
//            minSdk = 24
//            targetSdk = libs.findVersionInt("android_targetSdk")
//        }
//
////                configureFlavors(this)
//    }
//    extensions.configure<LibraryAndroidComponentsExtension> {
////                configurePrintApksTask(this)
//    }
//
//    configurations.configureEach {
//        resolutionStrategy {
//            force(libs.findLibrary("test-junit").get())
//            // Temporary workaround for https://issuetracker.google.com/174733673
//            force("org.objenesis:objenesis:2.6")
//        }
//    }
//    dependencies {
//        add("androidTestImplementation", kotlin("test"))
//        add("testImplementation", kotlin("test"))
//    }
//}