object Libraries {

    object Classpath {
        const val gradle = "com.android.tools.build:gradle:${Versions.classpathGradle}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val id: String = "dagger.hilt.android.plugin"
        const val gradleClasspath = "org.jetbrains.kotlin:kotlin-serialization:${Versions.serialization}"
    }

    object Storage {
        const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
        const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
        const val navigation = "androidx.navigation:navigation-compose:2.4.2"
    }

    object Lifecycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleCompose}"
    }

    object Room {
        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
        const val roomPaging = "androidx.room:room-paging:${Versions.room}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Navigation {
        const val compose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
        const val raam = "io.github.raamcosta.compose-destinations:core:${Versions.navigationRaam}"
        const val animations = "io.github.raamcosta.compose-destinations:animations-core:${Versions.navigationRaam}"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:${Versions.navigationRaam}"
    }

    object Others {
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }

    object Squareup {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    }

    object Ktor {
        private const val version = "2.0.2"
        const val client = "io.ktor:ktor-client-core:$version"
        const val engine = "io.ktor:ktor-client-android:$version"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val kotlinXJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    const val arrow = "io.arrow-kt:arrow-core:1.1.2"

    object Test {
        const val ext = "androidx.test.ext:junit:${Versions.ext}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val uiTestJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val junit = "junit:junit:${Versions.junit}"
        const val mockk = "io.mockk:mockk:1.12.4"
        const val truth = "com.google.truth:truth:1.1.3"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:1.6.2"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"

    }
}