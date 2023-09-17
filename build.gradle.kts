// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
// https://github.com/gradle/gradle/issues/22797
plugins {
    id("foodiestudio.android.library.compose") apply false
    alias(sharedLibs.plugins.sqldelight) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}