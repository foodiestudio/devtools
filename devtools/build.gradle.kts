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
    implementation(sharedLibs.bundles.compose.core)
    implementation(sharedLibs.accompanist.systemuicontroller)
    implementation(sharedLibs.accompanist.navigation.material)
    implementation(sharedLibs.okio)
    implementation("com.iqiyi.xcrash:xcrash-android-lib:3.0.0")
    api(sharedLibs.startup)
    testImplementation(sharedLibs.junit)
    // debugOnly
    debugImplementation(sharedLibs.activity.compose)
    debugImplementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    debugImplementation(sharedLibs.compose.material)
}

group = "com.github.foodiestudio"
version = "0.1.5"

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}