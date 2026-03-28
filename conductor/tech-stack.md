# Tech Stack

## Core
- **Kotlin:** Primary programming language.
- **Android SDK:** Target platform.
- **Gradle (Kotlin DSL):** Build system for project and dependency management.
- **Local Version Catalog:** Dependency management using `gradle/libs.versions.toml`.

## UI Framework
- **Jetpack Compose:** For building modern, declarative Android UIs.
- **Material 2 & 3:** UI component libraries.
- **Compose BOM:** Managed dependency versions via BOM `2023.10.01`.

## Dependencies
- **App Startup:** For automatic, low-overhead initialization.
- **Okio:** Efficient file I/O operations for storage inspection.
- **XCrash:** Crash monitoring and reporting utility.
- **Accompanist:**
  - **Navigation Material:** Integration of Material components into Compose Navigation.
  - **SystemUIController:** Controlling system UI (status bar, etc.) in Compose.
