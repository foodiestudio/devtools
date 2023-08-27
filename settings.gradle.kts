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
            from("io.github.foodiestudio:libs-versions:2023.01.00")
            library("startup", "androidx.startup:startup-runtime:1.1.1")
            val accompanistVersion = "0.32.0"
            library("accompanist-systemuicontroller", "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
            library("accompanist-navigation-material", "com.google.accompanist:accompanist-navigation-material:$accompanistVersion")
            library("okio", "com.squareup.okio:okio:3.5.0")
            library("navigation", "androidx.navigation:navigation-compose:2.6.0")
        }
    }
}
rootProject.name = "DevTools"
include(":devtools")
