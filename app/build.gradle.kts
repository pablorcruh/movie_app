import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "ec.com.pablorcruh.watch_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "ec.com.pablorcruh.watch_app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val configProperties = Properties()
        val configPropertiesFile = file("${project.rootDir}/config.properties")

        if(configPropertiesFile.exists()){
            configProperties.load(FileInputStream(configPropertiesFile))
        }

        buildConfigField("String", "API_KEY", "\"${configProperties["API_KEY"]}\"")

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
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

    kapt(libs.room.compiler)
    implementation(libs.room.paging)
    implementation(libs.room)

    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3)
    implementation(libs.okhttp.logging)

    implementation(libs.timber)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.coil)

    implementation(libs.ui.controller)

    implementation(libs.splash)

    implementation(libs.youtube.player)

    implementation(libs.dagger.hilt)
    implementation(libs.dagger.compose.navigation)
    kapt(libs.dagger.google.compiler)
    kapt(libs.dagger.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}