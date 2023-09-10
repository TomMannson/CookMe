@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.tom.application)
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp").version("1.8.22-1.0.11")
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

//    implementation("com.patrykandpatrick.vico:core:1.6.5")
//    implementation("com.patrykandpatrick.vico:compose:1.6.5")


//    ksp(libs.raamcosta.composeDestinationscKsp)
//    implementation(libs.raamcosta.composeDestinationscCore)
//    implementation (libs.google.accompanistSystemUiController)
    implementation(libs.google.textRecognition)
    implementation(libs.kotlinx.immutableCollections)


//    implementation(platform(libs.compose.bom))
//
//    implementation(libs.core.ktx)
//    implementation(libs.lifecycle.runtime.ktx)
//    implementation(libs.activity.compose)
//    implementation(libs.ui)
//    implementation(libs.ui.graphics)
//    implementation(libs.ui.tooling.preview)
//    implementation(libs.material3)
//
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//    androidTestImplementation(platform(libs.compose.bom))
//    androidTestImplementation(libs.ui.test.junit4)
//    debugImplementation(libs.ui.tooling)
//    debugImplementation(libs.ui.test.manifest)
}