pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("foodiestudio")) {
                useModule("com.github.foodiestudio:convention-plugins:0.3.0")
            }
        }
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
            from("io.github.foodiestudio:libs-versions:2023.04.01")
        }
    }
}
rootProject.name = "DevTools"
include(":devtools")
