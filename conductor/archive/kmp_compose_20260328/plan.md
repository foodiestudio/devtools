# Implementation Plan: KMP Compose Migration & Dependency Updates

## Phase 1: Core Build Environment Migration [checkpoint: f965905]
This phase focuses on updating the build system and dependency catalog to support AGP 8.12.0, Kotlin 2.3.10, and Compose Multiplatform 1.10.3.

- [x] **Task: Update Version Catalog (libs.versions.toml)**
    - [x] Set `agp = "8.12.0"`
    - [x] Set `kotlin = "2.3.10"`
    - [x] Set `composeMultiplatform = "1.10.3"`
    - [x] Update other relevant dependencies if necessary for compatibility.
- [x] **Task: Migrate Convention Plugins to Kotlin 2.3.10**
    - [x] Update `convention-plugins/build.gradle.kts` if needed.
    - [x] Verify plugin compilation with new Kotlin version.
- [x] **Task: Integrate Compose Multiplatform Plugin**
    - [x] Replace `devtools.android.compose` (or its contents) with the Compose Multiplatform Gradle plugin.
    - [x] Update `AndroidCompose.kt` convention plugin to support KMP Compose.
- [x] **Task: Conductor - User Manual Verification 'Phase 1: Core Build Environment Migration' (Protocol in workflow.md)**

## Phase 2: devtools Module Migration
Refactor the main library module to use the new KMP Compose environment and fix `launchAsApplication` logic.

- [x] **Task: Refactor devtools/build.gradle.kts**
    - [x] Apply `org.jetbrains.compose` plugin.
    - [x] Clean up the `plugins` block and `launchAsApplication` toggle logic.
    - [x] Update dependencies to use KMP BOM and multiplatform artifacts.
- [x] **Task: Update Source Code for KMP Compose Compatibility**
    - [x] Replace `androidx.compose` imports with `org.jetbrains.compose` equivalents where applicable (though KMP Compose usually maps to `androidx.compose` on Android, the plugin configuration might change).
    - [x] Address any API changes between Jetpack Compose and KMP Compose 1.10.3.
- [x] **Task: Verify launchAsApplication Toggle**
    - [x] Test build as a library (`launchAsApplication = false`).
    - [x] Test build as an application (`launchAsApplication = true`).
- [~] **Task: Conductor - User Manual Verification 'Phase 2: devtools Module Migration' (Protocol in workflow.md)**

## Phase 3: Validation and Finalization [checkpoint: 769f0e2]
Finalize the migration by ensuring all tests pass and the library is publishable.

- [x] **Task: Run Unit Tests and Fix Regressions**
    - [x] Run `./gradlew test` and address any failures.
    - [x] Ensure >80% coverage is maintained.
- [x] **Task: Verify Publishing Process**
    - [x] Run `./gradlew publishToMavenLocal` and verify output.
- [x] **Task: Final Cleanup and Documentation**
    - [x] Update `tech-stack.md` to reflect new versions.
    - [x] Perform a final self-review.
- [x] **Task: Conductor - User Manual Verification 'Phase 3: Validation and Finalization' (Protocol in workflow.md)**
