

import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
        }
    }
}