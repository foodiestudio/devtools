import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    // debug only
    val launchAsApplication = false

    if (launchAsApplication) {
        id("devtools.android.application")
    } else {
        id("devtools.android.library")
    }
    id("devtools.android.compose")
    id("maven-publish")
}

val launchAsApplication = project.plugins.findPlugin("devtools.android.library") == null

android {
    namespace = "com.github.foodiestudio.devtools"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    val compose = (project.extensions.getByName("compose") as org.jetbrains.compose.ComposeExtension).dependencies
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)
    implementation(libs.compose.multiplatform.material.icons.extended)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.okio)
    implementation(libs.xcrash)
    api(libs.startup)
    testImplementation(libs.junit)
    // debugOnly
    debugImplementation(libs.activity.compose)
    debugImplementation(libs.compose.material)
}

group = "com.github.foodiestudio"
version = "0.1.8"

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
