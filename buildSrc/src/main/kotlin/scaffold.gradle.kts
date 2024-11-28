import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

interface ScaffoldPluginExtension {
    val launchAsApplication: Property<Boolean>
    val namespace: Property<String>
}

val scaffold = extensions.create<ScaffoldPluginExtension>("scaffold")
scaffold.launchAsApplication.convention(project.findProperty("scaffold.launchAsApplication")?.toString()?.toBoolean() ?: false)
scaffold.namespace.convention(project.findProperty("scaffold.namespace")?.toString())

if (scaffold.launchAsApplication.get()) {
    apply(plugin = "foodiestudio.android.application.compose")
    the<ApplicationExtension>().apply {
        namespace = scaffold.namespace.get()
        defaultConfig { testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" }
    }
} else {
    apply(plugin = "foodiestudio.android.library.compose")
    apply(plugin = "maven-publish")
    the<LibraryExtension>().apply {
        namespace = scaffold.namespace.get()
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }
        buildTypes {
            release {
                isMinifyEnabled = false
                setProguardFiles(
                        listOf(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                        )
                )
            }
        }
        publishing { singleVariant("release") { withSourcesJar() } }
    }

    group = project.findProperty("scaffold.publishGroup")?.toString()!!
    version = project.findProperty("scaffold.publishVersion")?.toString()!!

    the<PublishingExtension>().apply {
        publications {
            register<MavenPublication>("release") { afterEvaluate { from(components["release"]) } }
        }
    }
}

