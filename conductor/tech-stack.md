# Tech Stack

## Core
- **Kotlin:** Primary programming language (v2.3.10).
- **Android SDK:** Target platform (compileSdk 35).
- **Gradle (Kotlin DSL):** Build system for project and dependency management (AGP v8.12.0).
- **Local Convention Plugins:** Modular build logic defined in `convention-plugins/`.
- **Local Version Catalog:** Dependency management using `gradle/libs.versions.toml`.

## UI Framework
- **Compose Multiplatform (KMP Compose):** For building modern, declarative Android UIs (v1.10.3).
- **Material 2 & 3:** UI component libraries.
- **Compose BOM:** Managed dependency versions via BOM `2023.10.01`. (Note: KMP Compose migration may reduce BOM usage for multiplatform components).

## Dependencies
- **App Startup:** For automatic, low-overhead initialization.
- **Okio:** Efficient file I/O operations for storage inspection.
- **XCrash:** Crash monitoring and reporting utility.
- **Accompanist:**
  - **Navigation Material:** Integration of Material components into Compose Navigation.
  - **SystemUIController:** Controlling system UI (status bar, etc.) in Compose.
