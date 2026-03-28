# Specification: Dependency Refactoring

## Goal
Replace the external `sharedLibs` version catalog with a local project version catalog (`gradle/libs.versions.toml`) without changing the underlying dependencies or their versions.

## Current State
- `settings.gradle.kts` imports `sharedLibs` from `io.github.foodiestudio:libs-versions:2023.10.01`.
- Project uses `sharedLibs` in `devtools/build.gradle.kts`.

## Target State
- `gradle/libs.versions.toml` exists in the project root.
- `settings.gradle.kts` no longer imports the external `sharedLibs` catalog.
- `devtools/build.gradle.kts` uses the local catalog (conventionally named `libs`).
- Dependency graph for `:devtools:dependencies` remains identical.

## Constraints
- **Zero Regression:** No dependency versions should change during the transition.
- **Verification:** Use the `./gradlew :devtools:dependencies` task to compare before and after states.
- **Naming:** Follow standard Android/Gradle naming conventions for the local version catalog (`libs`).
