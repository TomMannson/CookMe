package com.tommannson.androidplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidXPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.all {
//            when(this){
//                is AppPlugin
//            }
        }

        project.task("hello") {
//            doLast {
//                println("Hello from the GreetingPlugin")
//            }
        }
    }
}

// Apply the plugin
