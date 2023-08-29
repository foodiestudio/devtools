@Suppress("DSL_SCOPE_VIOLATION")
// https://github.com/gradle/gradle/issues/22797
plugins {
    alias(sharedLibs.plugins.android.library)
    alias(sharedLibs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.github.foodiestudio.devtools"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        buildConfig = false
        aidl = false
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = sharedLibs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    val composeBom = platform(sharedLibs.compose.bom)
    implementation(composeBom)
    implementation(sharedLibs.bundles.compose.core)
    implementation(sharedLibs.accompanist.systemuicontroller)
    implementation(sharedLibs.accompanist.navigation.material)
    implementation(sharedLibs.okio)
    api(sharedLibs.startup)

    testImplementation(sharedLibs.junit)
}

group = "com.github.foodiestudio"
version = "0.1.0"

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}