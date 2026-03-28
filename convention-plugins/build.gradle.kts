plugins {
    `kotlin-dsl`
}

group = "com.github.foodiestudio.devtools.buildlogic"

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "devtools.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "devtools.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "devtools.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.compose.multiplatform.gradlePlugin)
}
