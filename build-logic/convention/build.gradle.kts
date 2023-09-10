plugins {
    `kotlin-dsl`
}

group = "com.tommannson.buildlogic"
version = "0.5"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("common") {
            id = "tommannson.android.platform"
            implementationClass = "CommonConventionPlugin"
        }
        register("defaultsPlugin") {
            id = "tommannson.android.platform.defaults"
            implementationClass = "com.tommannson.convention.defaults.DefaultsConventionPlugin"
        }
        register("androidLibraryPlugin") {
            id = "tommannson.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidAppPlugin") {
            id = "tommannson.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("kotlinPlugin") {
            id = "tommannson.kotlin.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
    }
}