@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.tom.application)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt")
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}


val retrofit_version = "2.9.0"

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.remote)
    implementation(projects.feature.recipe)

    implementation(libs.imagepicker)

    implementation(libs.google.textRecognition)
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.playServices)
    implementation(libs.androidx.datastore)
    implementation(libs.log.timber)
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-compiler:2.49")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation (libs.voyager.navigator)
    implementation (libs.voyager.screenmodel)
    implementation (libs.voyager.hiltintegration)
}

kapt {
    correctErrorTypes = true
}