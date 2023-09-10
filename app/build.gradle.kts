@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.tom.application)
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp").version("1.9.10-1.0.13")
}

androidApp {
    namespace = "com.tommannson.familycooking"

    defaultConfig {
        applicationId = "com.tommannson.familycooking"
        versionCode = 1
        versionName = "0.5"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.google.textRecognition)
    implementation(libs.kotlinx.immutableCollections)
}