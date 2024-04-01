class AppConfig {
    val id = "com.clean_architecture.hilt_mvvm"
    val versionCode = 1
    val versionName = "1.0"

    val compileSdk = libs.versions.compileSdk.get().toInt()
    val minSdk = libs.versions.minSdk.get().toInt()
    val targetSdk = libs.versions.targetSdk.get().toInt()

    //    val javaVersion = JavaVersion.VERSION_1_8
    val javaVersion = JavaVersion.VERSION_17
    val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.android)
}

val appConfig = AppConfig()

android {
    namespace = appConfig.id
    compileSdk = 33

    defaultConfig {
        applicationId = appConfig.id

        minSdk = 23
        targetSdk = 33
        versionCode = appConfig.versionCode
        versionName = appConfig.versionName

        testInstrumentationRunner = appConfig.testInstrumentationRunner
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
        sourceCompatibility = appConfig.javaVersion
        targetCompatibility = appConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = appConfig.javaVersion.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

//    implementation(libs.androidx.activity)
    //Compile time dependencies
    kapt(libs.androidx.lifecycle.compiler)

    implementation(libs.kotlin.stdlib.jdk8)

    // Application dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
//    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.fragment.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.extensions)

//    implementation(libs.androidx.annotation)
    implementation(libs.glide)
    implementation(libs.converter.gson)

    implementation(libs.swipe.refresh)


//    #Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
//    implementation(libs.hilt.lifecycle.viewmodel)

//    #Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
//    #optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    // Development/Tooling dependencies
    debugImplementation(libs.leakcanary.android)
    // Unit/Integration tests dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}