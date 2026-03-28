import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val isApp = hasProperty("launchAsApplication") && property("launchAsApplication") == "true"
            
            with(pluginManager) {
                if (isApp) {
                    apply("com.android.application")
                } else {
                    apply("com.android.library")
                }
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    if (isApp) {
                        // Application specific setup if needed
                    } else {
                        publishLibraryVariants("release", "debug")
                    }
                }

                sourceSets.apply {
                    val commonMain = getByName("commonMain")
                    val androidMain = getByName("androidMain")
                    
                    commonMain.dependencies {
                        // Core KMP dependencies
                    }
                    
                    androidMain.dependencies {
                        // Android specific dependencies
                    }
                }
            }

            if (isApp) {
                extensions.configure<ApplicationExtension> {
                    configureAndroidBase(this)
                    defaultConfig.targetSdk = 34
                }
            } else {
                extensions.configure<LibraryExtension> {
                    configureAndroidBase(this)
                    defaultConfig.targetSdk = 34
                }
            }
        }
    }
}
