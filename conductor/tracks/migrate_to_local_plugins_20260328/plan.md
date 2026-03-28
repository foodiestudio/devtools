# Implementation Plan: Migrate to Local Convention Plugins

## Phase 1: Initialize Local Build Logic [checkpoint: bb7d0d9]
- [x] Task: Create a new module (e.g., `build-logic`) to host the convention plugins.
    - [x] Add the module to `settings.gradle.kts`.
    - [x] Configure `build-logic/build.gradle.kts` with the necessary dependencies (AGP, Kotlin, Compose).
    - [x] Create the folder structure for the local convention plugins.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Initialize Local Build Logic' (Protocol in workflow.md)

## Phase 2: Implement Local Convention Plugins [checkpoint: 584e869]
- [x] Task: Implement the `com.android.application` convention plugin.
- [x] Task: Implement the `com.android.library` convention plugin.
- [x] Task: Implement the `org.jetbrains.kotlin.android` and Compose convention plugins.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Implement Local Convention Plugins' (Protocol in workflow.md)

## Phase 3: Migrate Project to Local Plugins [checkpoint: c2397b3]
- [x] Task: Update the root and module-level `build.gradle.kts` files to apply the new local plugins instead of the external ones.
- [x] Task: Remove all references to the external `foodiestudio` convention plugins.
- [x] Task: Conductor - User Manual Verification 'Phase 3: Migrate Project to Local Plugins' (Protocol in workflow.md)

## Phase 4: Validation and Cleanup
- [x] Task: Perform a full clean and build to ensure all plugins are correctly applied and dependencies resolved.
- [x] Task: Run `./gradlew check` to verify the project's integrity and ensure no regressions.
- [x] Task: Conductor - User Manual Verification 'Phase 4: Validation and Cleanup' (Protocol in workflow.md)
