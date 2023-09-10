package com.tommannson.convention.extensions.kotlinlib

import com.google.samples.apps.nowinandroid.getDefaults
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

open class KotlinLibExExtension {
    companion object {
        fun Project.kotlinLibEx() =
            extensions.create("kotlinLibEx", KotlinLibExExtension::class.java)
    }
}

@Suppress("UnstableApiUsage")
fun Project.kotlinLib(
    configure: Action<JavaPluginExtension> = Action { }
) {
    extensions.configure<JavaPluginExtension> {

        val defaults = getDefaults()

        with(defaults.androidConfig) {
            this@configure.targetCompatibility = this.targetCompatibility
            this@configure.sourceCompatibility = this.sourceCompatibility
        }
    }

    extensions.configure("java", configure)
}