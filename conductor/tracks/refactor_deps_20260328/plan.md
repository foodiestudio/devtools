# Implementation Plan: Dependency Refactoring

## Phase 0: Baseline Capture
- [ ] Task: Capture current dependencies baseline.
    - [ ] Run `./gradlew :devtools:dependencies > baseline_deps.txt`
    - [ ] Commit `baseline_deps.txt` (This is a temporary file for validation).
- [ ] Task: Conductor - User Manual Verification 'Phase 0: Baseline Capture' (Protocol in workflow.md)

## Phase 1: Local Version Catalog Initialization
- [ ] Task: Identify and extract dependencies from `sharedLibs`.
    - [ ] Examine `devtools/build.gradle.kts` to list all `sharedLibs.*` usage.
    - [ ] Determine the specific versions for each dependency from the external `libs-versions` project or by inspecting the local dependency resolution results.
- [ ] Task: Create `gradle/libs.versions.toml`.
    - [ ] Create the `gradle` directory if it doesn't exist.
    - [ ] Write identified versions and libraries to `gradle/libs.versions.toml`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Local Version Catalog Initialization' (Protocol in workflow.md)

## Phase 2: Transition to Local Catalog
- [ ] Task: Update `settings.gradle.kts` to point to the local version catalog.
    - [ ] Remove the `create("sharedLibs")` from the `versionCatalogs` block.
    - [ ] Ensure the local catalog is correctly recognized (Gradle looks for `gradle/libs.versions.toml` by default).
- [ ] Task: Update `devtools/build.gradle.kts` to use the local catalog.
    - [ ] Replace `sharedLibs.*` references with `libs.*` references.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Transition to Local Catalog' (Protocol in workflow.md)

## Phase 3: Validation and Finalization
- [ ] Task: Validate dependency graph consistency.
    - [ ] Run `./gradlew :devtools:dependencies > post_refactor_deps.txt`.
    - [ ] Compare `baseline_deps.txt` and `post_refactor_deps.txt` to ensure no differences.
- [ ] Task: Final cleanup.
    - [ ] Delete `baseline_deps.txt` and `post_refactor_deps.txt`.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Validation and Finalization' (Protocol in workflow.md)
