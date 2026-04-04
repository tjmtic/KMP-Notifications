plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    `maven-publish`
}

group = "com.abyxcz.viewpoint.notification"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core.ktx)
            }
        }
    }
}

android {
    namespace = "com.abyxcz.viewpoint.notification"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}
