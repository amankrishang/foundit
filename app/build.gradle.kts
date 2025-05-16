plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services) // Google Services plugin for Firebase
}

android {
    namespace = "com.example.foundit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foundit"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {
    implementation(libs.appcompat)

    // Updated Material Components with explicit version (1.9.0)
    implementation("com.google.android.material:material:1.9.0")

    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase dependencies you need:
    implementation(libs.firebase.auth)  // Firebase Auth (already present)
    implementation("com.google.firebase:firebase-database:20.2.1") // Firebase Realtime Database

    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
