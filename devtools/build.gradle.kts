plugins {
    id("scaffold")
}

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
}

dependencies {
    implementation(sharedLibs.bundles.compose.core)
    implementation(sharedLibs.accompanist.systemuicontroller)
    implementation(sharedLibs.accompanist.navigation.material)
    implementation(sharedLibs.okio)
    implementation("com.iqiyi.xcrash:xcrash-android-lib:3.1.0")
    api(sharedLibs.startup)
    testImplementation(sharedLibs.junit)
    // debugOnly
    debugImplementation(sharedLibs.activity.compose)
    debugImplementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    debugImplementation(sharedLibs.compose.material)
}