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
    jvm()
    
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tjmtic/KMP-Notifications")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String? ?: "USER_NOT_SET"
                password = System.getenv("GITHUB_TOKEN") ?: System.getenv("GPR_READ_TOKEN") ?: project.findProperty("gpr.key") as String? ?: "TOKEN_NOT_SET"
            }
        }
    }
}
