# Implementation Plan: KMP Migration and Dependency Upgrades

## Phase 1: Environment and Version Alignment [checkpoint: 0c89341]
- [x] Task: Update `gradle/libs.versions.toml` with target versions.
    - [x] Set `agp = "8.12.0"`.
    - [x] Set `kotlin = "2.3.10"`.
    - [x] Set `compose = "1.10.3"`.
    - [x] Update other dependencies (Okio, etc.) to KMP-compatible versions.
- [x] Task: Update `convention-plugins` to support Kotlin 2.3.10 and AGP 8.12.0.
    - [x] Update `AndroidBase.kt` if needed for AGP 8.12.0 compatibility.
    - [x] Update `AndroidCompose.kt` to use JetBrains Compose plugin logic.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Environment and Version Alignment' (Protocol in workflow.md)

## Phase 2: Convert `devtools` to Multiplatform [checkpoint: 094d2be]
- [x] Task: Create `devtools.kmp.library` convention plugin.
    - [x] Add KMP plugin to `convention-plugins`.
    - [x] Configure `androidTarget` and basic `sourceSets`.
- [x] Task: Transition `devtools/build.gradle.kts` to KMP.
    - [x] Replace `devtools.android.library` with `devtools.kmp.library`.
    - [x] Configure KMP `sourceSets` (`commonMain`, `androidMain`).
- [x] Task: Reorganize source code directory structure.
    - [x] Move `src/main/java` to `src/androidMain/kotlin`.
    - [x] Move `src/main/res` to `src/androidMain/res`.
    - [x] Create `src/commonMain/kotlin`.
- [x] Task: Conductor - User Manual Verification 'Phase 2: Convert devtools to Multiplatform' (Protocol in workflow.md)

## Phase 3: Migrate Compose and Dependencies
- [x] Task: Migrate imports from `androidx.compose` to KMP Compose.
    - [x] Update UI components in `devtools` to use multiplatform Compose APIs.
- [x] Task: Finalize dependency migration in `commonMain` and `androidMain`.
    - [x] Ensure `Okio` and other utilities are correctly linked in `commonMain` or `androidMain`.
- [x] Task: Conductor - User Manual Verification 'Phase 3: Migrate Compose and Dependencies' (Protocol in workflow.md)

## Phase 4: Validation and Regression Testing
- [ ] Task: Verify Android build integrity.
    - [ ] Run `./gradlew :devtools:assembleDebug`.
- [ ] Task: Run regression tests.
    - [ ] Execute `./gradlew :devtools:test`.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Validation and Regression Testing' (Protocol in workflow.md)
