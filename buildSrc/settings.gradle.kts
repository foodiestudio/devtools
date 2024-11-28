dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven("https://jitpack.io")
    }
    versionCatalogs {
        create("buildLibs") {
            from("io.github.foodiestudio:libs-versions:2023.10.01")
        }
    }
}