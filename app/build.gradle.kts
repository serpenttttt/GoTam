plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.gotam_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gotam_project"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val mapkitApiKey: String = project.extensions.extraProperties["mapkitApiKey"] as String

        // Добавляем API-ключ в BuildConfig и Manifest
        buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
        manifestPlaceholders["MAPKIT_API_KEY"] = mapkitApiKey

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room
    implementation(libs.room.runtime.v261)
    implementation(libs.androidx.room.ktx.v261)
    kapt("androidx.room:room-compiler:2.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Фрагменты
    implementation(libs.androidx.fragment.ktx)

    // Дополнительные зависимости
    implementation (libs.androidx.core.ktx.v1120)
    implementation (libs.play.services.location.v2101)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose.v172)

    // Coil для загрузки изображений
    implementation(libs.coil.compose)

    // Compose Animation
    implementation(libs.androidx.animation)

    // Yandex MapKit
    implementation (libs.accompanist.permissions)
    implementation("com.yandex.android:maps.mobile:4.13.0-full")
    implementation ("com.google.android.gms:play-services-location:21.3.0")

    // WorkManager
    implementation (libs.androidx.work.runtime.ktx.v281)

    // SavedStateHandle
    implementation( libs.androidx.lifecycle.viewmodel.compose)

    // Уведомления
    implementation (libs.androidx.core.ktx.v1101)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}