import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}


val mapkitApiKey: String by lazy {
    val properties = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { properties.load(it) }
    }
    properties.getProperty("MAPKIT_API_KEY", "")
}

// для доступа в app/build.gradle.kts
allprojects {

    extensions.extraProperties["mapkitApiKey"] = mapkitApiKey
}