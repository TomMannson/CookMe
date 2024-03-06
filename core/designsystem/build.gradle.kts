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
    api(libs.androidx.constraintLayout)

    api(libs.androidx.activityCompose)
    api("androidx.hilt:hilt-navigation-compose:1.1.0")
    api("androidx.navigation:navigation-fragment-ktx:2.7.6")
    api("androidx.navigation:navigation-ui-ktx:2.7.6")
    api("androidx.navigation:navigation-compose:2.7.6")

    // export into plugin
    testApi(libs.bundles.test.core)
    androidTestApi(libs.bundles.test.android)
}