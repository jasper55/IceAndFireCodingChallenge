plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") version ("1.6.10-1.0.2")
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    defaultConfig {
        applicationId = AppConfig.applicationId
        buildToolsVersion = AppConfig.buildToolsVersion

        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        compileSdk = AppConfig.compileSdk

        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), AppConfig.proguardConsumerRules)
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.activityCompose)

    implementation(Libraries.Compose.ui)
    implementation(Libraries.Compose.uiTooling)
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.materialExtended)
    implementation(Libraries.Compose.hiltNavigation)
    implementation(Libraries.Compose.paging)

    implementation(Libraries.Lifecycle.runtime)
    implementation(Libraries.Lifecycle.viewModel)
    implementation(Libraries.Lifecycle.viewModelCompose)

    implementation(Libraries.Room.room)
    implementation(Libraries.Room.roomKtx)
    implementation(Libraries.Room.roomPaging)
    kapt(Libraries.Room.roomKapt)

    implementation(Libraries.Storage.dataStore)

    implementation(Libraries.Hilt.hilt)
    kapt(Libraries.Hilt.hiltKapt)

    implementation(Libraries.Navigation.compose)
    implementation(Libraries.Navigation.raam)
    ksp(Libraries.Navigation.ksp)

    implementation(Libraries.Squareup.retrofit)
    implementation(Libraries.Squareup.loggingInterceptor)
    implementation(Libraries.Squareup.moshiConverter)
    implementation(Libraries.Squareup.moshi)

    implementation(Libraries.Others.gson)
    implementation(Libraries.arrow)

    implementation(Libraries.Ktor.client)
    implementation(Libraries.Ktor.engine)
    implementation(Libraries.Ktor.contentNegotiation)
    implementation(Libraries.Ktor.kotlinXJson)

    testImplementation(Libraries.Test.junit)
    androidTestImplementation(Libraries.Test.ext)
    androidTestImplementation(Libraries.Test.espresso)
    androidTestImplementation(Libraries.Test.uiTestJunit)
}
