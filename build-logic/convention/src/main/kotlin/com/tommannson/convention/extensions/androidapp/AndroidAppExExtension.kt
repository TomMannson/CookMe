package org.gradle.kotlin.dsl

//import configureDefaults
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.google.samples.apps.nowinandroid.configureComposeAndroid
import com.google.samples.apps.nowinandroid.configureKotlinAndroid
import com.google.samples.apps.nowinandroid.getDefaults
import com.google.samples.apps.nowinandroid.setupAppSdk
import com.tommannson.convention.defaults.Defaults
import org.gradle.api.Action
import org.gradle.api.Project

open class AndroidAppExExtension {
    companion object {
        fun Project.androidAppEx() {
            extensions.create("androidAppEx", AndroidAppExExtension::class.java)
        }
    }
}

@Suppress("UnstableApiUsage")
fun Project.androidApp(
    configure: Action<BaseAppModuleExtension>
) {
    extensions.configure<AndroidAppExExtension> {

        extensions.configure<BaseAppModuleExtension> {

            setupAppSdk(this)

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

//            testOptions {
//                unitTests.all {
//                    useJUnitPlatform()
//                }
//            }

            configureKotlinAndroid(this)
            configureComposeAndroid(this)
        }

        extensions.configure("android", configure)
    }
}