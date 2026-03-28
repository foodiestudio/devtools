# Specification: Migrate to Kotlin Multiplatform (KMP)

## Overview
This track involves migrating the existing `devtools` Android library to a Kotlin Multiplatform (KMP) structure. While the initial focus remains on Android support, the project will be reorganized into a KMP layout, and core dependencies will be upgraded to their latest stable or specified versions.

## Goals
- **Modernization:** Upgrade Kotlin to 2.3.10, AGP to 8.12.0, and Compose to 1.10.3 (JetBrains Compose).
- **Structure:** Transition the project to a KMP project layout (e.g., using `commonMain`, `androidMain`).
- **Dependency Management:** Migrate dependencies to KMP-compatible versions where possible (KMP First strategy).
- **Maintainability:** Ensure existing Android functionality remains intact through regression testing.

## Functional Requirements
- **Dependency Upgrades:**
    - Kotlin: `2.3.10`.
    - AGP: `8.12.0`.
    - JetBrains Compose (KMP): `1.10.3`.
- **Project Reorganization:**
    - Convert `devtools` module to a KMP module.
    - Move core logic to `commonMain`.
    - Keep Android-specific UI and logic in `androidMain`.
- **Resource Management:**
    - Continue to use Android-specific resources (drawables, XML) within the `androidMain` source set.
- **Dependency Migration:**
    - Update `libs.versions.toml` with KMP versions of dependencies (e.g., Okio).

## Acceptance Criteria
- Project successfully syncs and builds with Kotlin 2.3.10 and AGP 8.12.0.
- All `devtools` functionality is accessible from the Android platform.
- The project uses JetBrains Compose (KMP) instead of AndroidX Compose.
- All existing regression tests pass.
- `libs.versions.toml` is updated and cleaned of legacy AndroidX Compose references.

## Out of Scope
- Implementation of iOS, Desktop, or Web targets in this phase.
- Migration of Android resources to `compose.resources`.
- Refactoring of internal UI logic to be platform-agnostic beyond what is required for the KMP structure.
