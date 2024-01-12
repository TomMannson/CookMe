@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.tom.library)
}

androidLib {
    namespace = "com.tommannson.remote"
}

android {
    buildFeatures {
        androidResources = false
    }
}

dependencies {
    api(libs.retrofit)
    api(libs.retrofitConverterScalars)
}