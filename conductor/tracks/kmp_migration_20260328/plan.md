# Implementation Plan: KMP Migration and Dependency Upgrades

## Phase 1: Environment and Version Alignment
- [ ] Task: Update `gradle/libs.versions.toml` with target versions.
    - [ ] Set `agp = "8.12.0"`.
    - [ ] Set `kotlin = "2.3.10"`.
    - [ ] Set `compose = "1.10.3"`.
    - [ ] Update other dependencies (Okio, etc.) to KMP-compatible versions.
- [ ] Task: Update `convention-plugins` to support Kotlin 2.3.10 and AGP 8.12.0.
    - [ ] Update `AndroidBase.kt` if needed for AGP 8.12.0 compatibility.
    - [ ] Update `AndroidCompose.kt` to use JetBrains Compose plugin logic.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Environment and Version Alignment' (Protocol in workflow.md)

## Phase 2: Convert `devtools` to Multiplatform
- [ ] Task: Create `devtools.kmp.library` convention plugin.
    - [ ] Add KMP plugin to `convention-plugins`.
    - [ ] Configure `androidTarget` and basic `sourceSets`.
- [ ] Task: Transition `devtools/build.gradle.kts` to KMP.
    - [ ] Replace `devtools.android.library` with `devtools.kmp.library`.
    - [ ] Configure KMP `sourceSets` (`commonMain`, `androidMain`).
- [ ] Task: Reorganize source code directory structure.
    - [ ] Move `src/main/java` to `src/androidMain/kotlin`.
    - [ ] Move `src/main/res` to `src/androidMain/res`.
    - [ ] Create `src/commonMain/kotlin`.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Convert devtools to Multiplatform' (Protocol in workflow.md)

## Phase 3: Migrate Compose and Dependencies
- [ ] Task: Migrate imports from `androidx.compose` to KMP Compose.
    - [ ] Update UI components in `devtools` to use multiplatform Compose APIs.
- [ ] Task: Finalize dependency migration in `commonMain` and `androidMain`.
    - [ ] Ensure `Okio` and other utilities are correctly linked in `commonMain` or `androidMain`.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Migrate Compose and Dependencies' (Protocol in workflow.md)

## Phase 4: Validation and Regression Testing
- [ ] Task: Verify Android build integrity.
    - [ ] Run `./gradlew :devtools:assembleDebug`.
- [ ] Task: Run regression tests.
    - [ ] Execute `./gradlew :devtools:test`.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Validation and Regression Testing' (Protocol in workflow.md)
