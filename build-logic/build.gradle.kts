plugins {
    `kotlin-dsl`
}

group = "com.github.foodiestudio.devtools.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}
