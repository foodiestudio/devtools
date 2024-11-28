import com.android.build.api.dsl.LibraryExtension

interface ScaffoldPluginExtension {
    val launchAsApplication: Property<Boolean>
    val namespace: Property<String>
}

val scaffold = extensions.create<ScaffoldPluginExtension>("scaffold")
scaffold.launchAsApplication.convention(
    project.findProperty("scaffold.launchAsApplication")?.toString()?.toBoolean() ?: false
)

if (scaffold.launchAsApplication.get()) {
    apply(plugin = "foodiestudio.android.application.compose")
} else {
    apply(plugin = "foodiestudio.android.library.compose")
    apply(plugin = "maven-publish")
    the<LibraryExtension>().apply {
        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
        publishing { singleVariant("release") { withSourcesJar() } }
    }
    group = project.findProperty("scaffold.publishGroup")?.toString()!!
    version = project.findProperty("scaffold.publishVersion")?.toString()!!

    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("release") { afterEvaluate { from(components["release"]) } }
        }
    }
}

