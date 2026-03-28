# Specification: Migrate to Local Convention Plugins

## Overview
This track involves removing the dependency on the external `foodiestudio` convention plugins and migrating to a set of local convention plugins defined within a separate build-logic module. These local plugins will use standard Android (Application and Library) and Compose plugins, leveraging the `libs` version catalog for dependency management.

## Goals
- **Independence:** Remove the dependency on the external `foodiestudio` convention plugin.
- **Maintainability:** Define clear, local convention plugins that are easy to understand and modify.
- **Standardization:** Use standard Android Gradle Plugin (AGP) and Kotlin plugins as the foundation.
- **Consistency:** Ensure that dependency and version management remains centralized in the `libs` version catalog.

## Functional Requirements
- **Local Convention Plugins:**
    - Create a new module (e.g., `build-logic`) to host the local convention plugins.
    - Implement plugins for:
        - Android Application (using `com.android.application`)
        - Android Library (using `com.android.library`)
        - Compose (using `org.jetbrains.kotlin.android` and the standard Compose compiler/library integrations)
- **Migration:**
    - Update the root and module-level `build.gradle.kts` files to apply the new local plugins instead of the external ones.
- **Validation:**
    - Ensure that the project builds successfully and all functionalities (App, Library, and Compose) are preserved.

## Acceptance Criteria
- External `foodiestudio` convention plugins are completely removed from the project.
- New local convention plugins are implemented in a separate module and correctly applied to relevant modules.
- The project successfully syncs, builds, and runs.
- All dependencies are still correctly resolved from the `libs` version catalog.
- `gradlew check` passes without regression.

## Out of Scope
- Major version upgrades for AGP, Kotlin, or Compose (unless required for compatibility during the migration).
- Functional changes or feature additions to the `devtools` library.
