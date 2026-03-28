# Implementation Plan: Dependency Refactoring

## Phase 0: Baseline Capture [checkpoint: b0710d2]
- [x] Task: Capture current dependencies baseline.
    - [x] Run `./gradlew :devtools:dependencies > baseline_deps.txt`
    - [x] Commit `baseline_deps.txt` (This is a temporary file for validation).
- [x] Task: Conductor - User Manual Verification 'Phase 0: Baseline Capture' (Protocol in workflow.md)

## Phase 1: Local Version Catalog Initialization [checkpoint: fb18167]
- [x] Task: Identify and extract dependencies from `sharedLibs`.
    - [x] Examine `devtools/build.gradle.kts` to list all `sharedLibs.*` usage.
    - [x] Determine the specific versions for each dependency from the external `libs-versions` project or by inspecting the local dependency resolution results.
- [x] Task: Create `gradle/libs.versions.toml`.
    - [x] Create the `gradle` directory if it doesn't exist.
    - [x] Write identified versions and libraries to `gradle/libs.versions.toml`.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Local Version Catalog Initialization' (Protocol in workflow.md)

## Phase 2: Transition to Local Catalog [checkpoint: 890f51f]
- [x] Task: Update `settings.gradle.kts` to point to the local version catalog.
    - [x] Remove the `create("sharedLibs")` from the `versionCatalogs` block.
    - [x] Ensure the local catalog is correctly recognized (Gradle looks for `gradle/libs.versions.toml` by default).
- [x] Task: Update `devtools/build.gradle.kts` to use the local catalog.
    - [x] Replace `sharedLibs.*` references with `libs.*` references.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Transition to Local Catalog' (Protocol in workflow.md)

## Phase 3: Validation and Finalization
- [x] Task: Validate dependency graph consistency.
    - [x] Run `./gradlew :devtools:dependencies > post_refactor_deps.txt`.
    - [x] Compare `baseline_deps.txt` and `post_refactor_deps.txt` to ensure no differences.
- [x] Task: Final cleanup.
    - [x] Delete `baseline_deps.txt` and `post_refactor_deps.txt`.
- [x] Task: Conductor - User Manual Verification 'Phase 3: Validation and Finalization' (Protocol in workflow.md)
