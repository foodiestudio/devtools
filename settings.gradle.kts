pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    versionCatalogs {
        create("sharedLibs") {
            from("io.github.foodiestudio:libs-versions:2023.04.00")
            library("startup", "androidx.startup:startup-runtime:1.1.1")
            val accompanistVersion = "0.30.1"
            library("accompanist-systemuicontroller", "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
            library("accompanist-navigation-material", "com.google.accompanist:accompanist-navigation-material:$accompanistVersion")
            library("okio", "com.squareup.okio:okio:3.7.0")
        }
    }
}
rootProject.name = "DevTools"
include(":devtools")
