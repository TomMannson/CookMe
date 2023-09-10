@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.tom.library)
}

androidLib {
    namespace = "com.tommannson.designsystem"
}

android {
    buildFeatures {
        androidResources = true
    }
}

dependencies {
    api(libs.composeUi)
    api(libs.google.material)
    api(libs.compose.icons)

    api(libs.androidx.activityCompose)

    // export into plugin
    testApi(libs.bundles.test.core)
    androidTestApi(libs.bundles.test.android)
}