package com.tommannson.convention.config

import org.gradle.api.JavaVersion

data class AndroidConfig internal constructor(
    val minSdkVersion: Int,
    val sdkVersion: Int,
    val composeEnabled: Boolean = false,
    val sourceCompatibility: JavaVersion,
    val targetCompatibility: JavaVersion
)

data class AndroidConfigBuilder internal constructor(
    var minSdk: Int = 24,
    var sdk: Int = 33,
    var composeEnabled: Boolean = false,
    var sourceCompatibility: JavaVersion = JavaVersion.VERSION_1_8,
    var targetCompatibility: JavaVersion = sourceCompatibility
) {

    internal fun build() = AndroidConfig(
        minSdk,
        sdk,
        composeEnabled,
        sourceCompatibility,
        targetCompatibility
    )

}

