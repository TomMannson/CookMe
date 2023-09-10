/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryDefaultConfig
import com.tommannson.convention.defaults.Defaults
import com.tommannson.convention.defaults.defaultsKey
import com.tommannson.convention.ext.findVersionString
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
    defaults: Defaults = getDefaults()
) {

    commonExtension.apply {
        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )

            with(defaults.androidConfig) {
                jvmTarget = targetCompatibility.toString()
            }


        }
    }

    dependencies {
//        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureComposeAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
    defaults: Defaults = getDefaults()
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    if (defaults.androidConfig.composeEnabled) {
        commonExtension.apply {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = libs.findVersionString("compose-compiler")
            }
        }

        dependencies {
            implementation(platform(libs.findVersionString("compose-bomFull")))

            debugImplementation(libs.findLibrary("composeUiTooling").get())
            debugImplementation(libs.findLibrary("composeUiTestManifest").get())
        }
    }
}

internal fun Project.setupAppSdk(
    commonExtension: CommonExtension<*, *, ApplicationDefaultConfig, *, *>,
    defaults: Defaults = getDefaults()
) {

    commonExtension.apply {

        with(defaults.androidConfig) {

            compileSdk = sdkVersion
            defaultConfig {
                minSdk = minSdkVersion
                targetSdk = sdkVersion
            }

            compileOptions {
                val androidConfig = this@with
                sourceCompatibility = androidConfig.sourceCompatibility
                targetCompatibility = androidConfig.targetCompatibility
            }
        }
    }
}

internal fun Project.setupLibrarySdk(
    commonExtension: CommonExtension<*, *, LibraryDefaultConfig, *, *>,
    defaults: Defaults = getDefaults()
) {

    commonExtension.apply {

        with(defaults.androidConfig) {

            compileSdk = sdkVersion
            defaultConfig {
                minSdk = minSdkVersion

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                val androidConfig = this@with
                sourceCompatibility = androidConfig.sourceCompatibility
                targetCompatibility = androidConfig.targetCompatibility
            }
        }
    }
}

fun Project.getDefaults() =
    requireNotNull(rootProject.extra.get(defaultsKey)) { "defaults required" } as Defaults


fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any) {
    add("debugImplementation", dependencyNotation)
}