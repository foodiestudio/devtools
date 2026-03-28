import com.android.build.api.dsl.LibraryDefaultConfig
import org.jetbrains.compose.compose

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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(libs.accompanist.systemuicontroller)
                implementation(libs.accompanist.navigation.material)
                implementation(libs.xcrash)
                implementation(libs.startup)
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
    debugImplementation(libs.compose.ui.tooling.preview)
}

group = "com.github.foodiestudio"
version = "0.1.8"
