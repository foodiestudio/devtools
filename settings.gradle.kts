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
            library("accompanist-systemuicontroller", "com.google.accompanist:accompanist-systemuicontroller:0.30.1")
        }
    }
}
rootProject.name = "DevTools"
include(":dev-tools")
