package com.tommannson.convention.defaults

import com.tommannson.convention.config.AndroidConfig
import com.tommannson.convention.config.AndroidConfigBuilder
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

internal val defaultsKey = "com.tommannson.convention.defaults"

data class Defaults(
    val androidConfig: AndroidConfig,
//    val kotlinConfig: KotlinConfig,
//    val defaults: TestConfig
) {
}

data class DefaultsBuilder(
    val androidConfig: AndroidConfigBuilder = AndroidConfigBuilder(),
//    val kotlinConfig: KotlinConfig,
//    val defaults: TestConfig
) {

    fun android(builder: AndroidConfigBuilder.() -> Unit) {
        androidConfig.builder()
    }

    fun build() = Defaults(
        androidConfig.build(),
//        kotlinConfig,
//        defaults
    )
}