plugins {
    id("kotlin-android")
    id("com.android.application")
    //id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    // Kotlin Annotation Processing Tool

    id("kotlin-kapt")
    // Dagger Hilt Plugin
    id("dagger.hilt.android.plugin")

}

android {
    namespace = "com.example.internetcafeapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.internetcafeapplication"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")

    // location
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    // Fuel library
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-android:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-json:2.3.1")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
//
    // Hilt Dependency
    implementation("com.google.dagger:hilt-android:2.44")

    // Hilt Compiler Dependency for Kotlin Annotation Processing
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Io DI?
    implementation ("io.insert-koin:koin-android:3.4.0")

    // Activity Compose
    implementation ("androidx.activity:activity-compose:1.4.0")

    // CameraX
    implementation ("androidx.camera:camera-camera2:1.0.2")
    implementation ("androidx.camera:camera-lifecycle:1.0.2")
    implementation ("androidx.camera:camera-view:1.0.0-alpha31")

    // Zxing
    implementation ("com.google.zxing:core:3.3.3")







}

kapt {
    correctErrorTypes = true
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8)) // Use Java 8
    }
}