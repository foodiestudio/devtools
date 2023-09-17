@Suppress("DSL_SCOPE_VIOLATION")
// https://github.com/gradle/gradle/issues/22797
plugins {
    id("foodiestudio.android.library.compose")
    id("maven-publish")
}

android {
    namespace = "com.github.foodiestudio.devtools"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
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
    api(sharedLibs.startup)

    testImplementation(sharedLibs.junit)
}

group = "com.github.foodiestudio"
version = "0.1.1"

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}