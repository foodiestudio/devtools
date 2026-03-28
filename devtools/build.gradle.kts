import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    // debug only
    val launchAsApplication = false

    if (launchAsApplication) {
        id("foodiestudio.android.application.compose")
    } else {
        id("foodiestudio.android.library.compose")
    }
    id("maven-publish")
}

val launchAsApplication = project.plugins.findPlugin("foodiestudio.android.library.compose") == null

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
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.core)
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
