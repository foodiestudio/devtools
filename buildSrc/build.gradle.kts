plugins {
    `kotlin-dsl`
}

dependencies {
    val agpVersion = buildLibs.versions.agp.get()
    val kotlinVersion = buildLibs.versions.kotlin.get()
    implementation("com.android.tools.build:gradle:$agpVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.github.foodiestudio:convention-plugins:0.5.4")
}