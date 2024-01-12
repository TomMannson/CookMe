import com.google.samples.apps.nowinandroid.kotlinOptions

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.tom.library)
    id("de.mannodermaus.android-junit5")
    id("io.gitlab.arturbosch.detekt")
}

androidLib {
    namespace = "com.tommannson.familycooking"

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
    implementation(projects.core.kotlinextensions)
}