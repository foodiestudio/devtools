import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    id("devtools.kmp.library")
    id("devtools.android.compose")
    id("maven-publish")
}

val launchAsApplication = project.hasProperty("launchAsApplication") && project.property("launchAsApplication") == "true"

android {
    namespace = "com.github.foodiestudio.devtools"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        if (!launchAsApplication) {
            (this as LibraryDefaultConfig).consumerProguardFiles("consumer-rules.pro")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.okio)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.compose.core)
                implementation(libs.accompanist.systemuicontroller)
                implementation(libs.accompanist.navigation.material)
                implementation(libs.xcrash)
                implementation(libs.startup)
                implementation(libs.compose.material)
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
}

dependencies {
    debugImplementation(libs.activity.compose)
}

group = "com.github.foodiestudio"
version = "0.1.8"

// Publishing for KMP is handled automatically by the KMP plugin.
// The previous manual publishing block is incompatible with KMP layout.
