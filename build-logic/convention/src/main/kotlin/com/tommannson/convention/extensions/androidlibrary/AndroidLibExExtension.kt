package org.gradle.kotlin.dsl

import com.android.build.api.dsl.LibraryExtension
import com.google.samples.apps.nowinandroid.configureComposeAndroid
import com.google.samples.apps.nowinandroid.configureKotlinAndroid
import com.google.samples.apps.nowinandroid.setupLibrarySdk
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


open class AndroidLibExExtension {
    companion object {
        fun Project.androidLibEx() {
            extensions.create("androidLibEx", AndroidLibExExtension::class.java)
        }
    }
}

@Suppress("UnstableApiUsage")
fun Project.androidLib(
    configure: Action<LibraryExtension>
) = extensions.configure<AndroidLibExExtension> {

    extensions.configure<com.android.build.gradle.LibraryExtension> {

        setupLibrarySdk(this)

//                configureFlavors(this)
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false // Enables code shrinking for the release build type.
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        configureKotlinAndroid(this)
        configureComposeAndroid(this)

        buildFeatures {
            androidResources = false
        }
    }

    extensions.configure<com.android.build.gradle.LibraryExtension>("android", configure)
}



