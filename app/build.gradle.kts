plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.light.sdk)
}

android {
    compileSdk = 36

    signingConfigs {
        // Workspace dev signing (same key as the SDK tools/emulator).
        create("lightsdkDev") {
            storeFile = file("../../light-sdk/sdk/keys/lightsdk-dev.jks")
            storePassword = "android"
            keyAlias = "lightsdk-dev"
            keyPassword = "android"
        }
    }

    defaultConfig {
        minSdk = 34
        targetSdk = 36

        // Consumed by the plugin's generated manifest (SDK_VERSION metadata).
        manifestPlaceholders["sdkVersion"] = property("sdkVersion") as String
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("lightsdkDev")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("lightsdkDev")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // SDK modules come from the included ../light-sdk build (see settings.gradle.kts).
    // Exclude the camera/ML Kit barcode stack (only used by the SDK's QR
    // scanner, which this tool never opens) — it drags in ~20 MB of native
    // libs (libbarhopper_v3.so) otherwise; APK-SHRINKING.md methodology.
    implementation(libs.sdk.client) {
        exclude(group = "com.google.mlkit")
        exclude(group = "androidx.camera")
    }
}
