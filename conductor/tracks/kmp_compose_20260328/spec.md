# Track Specification: Migrate to KMP Compose & Dependency Updates

## Overview
This track involves migrating the `devtools` library to use **Compose Multiplatform (KMP Compose)** and updating core build dependencies (AGP, Kotlin) to their latest target versions. While the immediate goal remains Android-only, the migration to KMP plugins provides a foundation for future multiplatform support.

## Objectives
- Migrate from Jetpack Compose to Compose Multiplatform.
- Update **AGP** to `8.12.0`.
- Update **Kotlin** to `2.3.10`.
- Update **Compose Multiplatform** to `1.10.3`.
- Ensure the `launchAsApplication` mechanism for local debugging/development still works correctly.

## Functional Requirements
- The `devtools` library must compile and run on Android.
- All existing features (App Storage Inspection, Kibana Query, Debug Entry Widget) must remain functional.
- The `launchAsApplication` toggle in `devtools/build.gradle.kts` must correctly switch between library and application modes.

## Non-Functional Requirements
- Maintain or improve build times.
- Ensure dependency management is centralized in `libs.versions.toml`.
- Adhere to existing code style guidelines.

## Acceptance Criteria
- [ ] Project builds successfully with AGP `8.12.0`, Kotlin `2.3.10`, and Compose Multiplatform `1.10.3`.
- [ ] `devtools` library is publishable as a Maven artifact.
- [ ] `launchAsApplication = true` correctly builds the project as an Android Application.
- [ ] `launchAsApplication = false` correctly builds the project as an Android Library.
- [ ] All unit tests pass.

## Out of Scope
- Adding support for iOS, Desktop, or Web targets in this track.
- Significant UI redesign or feature additions.
