import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        // Note: For Kotlin 2.0+, composeOptions.kotlinCompilerExtensionVersion is no longer used.
        // The compose-compiler is now a Gradle plugin.
        
        dependencies {
            // JetBrains Compose dependencies will be handled in the KMP sourceSets
            // but we keep the BOM for Android-specific parts if needed.
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
    
    // Apply the JetBrains Compose plugin
    pluginManager.apply("org.jetbrains.compose")
}
