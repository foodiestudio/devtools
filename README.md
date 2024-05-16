## dev tools
[![](https://jitpack.io/v/foodiestudio/devtools.svg)](https://jitpack.io/#foodiestudio/devtools)

### Preview
<p><img src="art/entry.jpeg" width="32%" /> <img src="art/app_storage.jpeg" width="32%" /> <img src="art/kibana.jpeg" width="32%" />

### Getting Started
```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //...
        maven("https://jitpack.io")
    }
}

// build.gradle.kts
dependencies {
    debugImplementation("com.github.foodiestudio:devtools:$version")
}
```

#### Initialization Instructions
The initialization component is registered through [App Startup](https://developer.android.com/topic/libraries/app-startup#kts) and is implicitly initialized, making it a plug-and-play tool.

However, you can also disable it using the `tools:node="remove"` method.

```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    android:exported="false"
    tools:node="merge">
    <meta-data
        android:name="com.github.foodiestudio.devtools.DevToolsInitializer"
        tools:node="remove" />
</provider>
```

Then manually call the initialization method.

```kotlin
DevToolsManager.init(applicationContext)
```

### Integrated Debugging
> Please refer to the [Gradle documentation](https://docs.gradle.org/current/samples/sample_composite_builds_declared_substitutions.html) for more information.

Temporarily integrate into the main project's `setting.gradle.kts`, and replace the specific version.

```kotlin
// e.g. ~/open-source/foodiestudio/devtools
includeBuild("/your_path_contain_this_project") {
    dependencySubstitution {
        substitute(module("com.github.foodiestudio:devtools"))
    }
}
```

For unpublished versions, you can use the [source dependencies](https://blog.gradle.org/introducing-source-dependencies) method to replace the includeBuild, which produces the same result.

```kotlin
// settings.gradle.kts
sourceControl {
    gitRepository(uri("https://github.com/foodiestudio/devtools.git")) {
        producesModule("com.github.foodiestudio:devtools")
    }
}

// build.gradle.kts
debugImplementation("com.github.foodiestudio:devtools") {
    version {
        // branch
        branch = "pre-release/0.1.0" 
    }
}
```
