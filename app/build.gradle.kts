plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.managerapp.personnelmanagerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.managerapp.personnelmanagerapp"
        minSdk = 29
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // **Core thư viện Android**
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    // **Material Design**
    implementation ("com.google.android.material:material:1.11.0")

    // **Retrofit & OkHttp (Gọi API)**
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // **Room Database (Lưu trữ dữ liệu)**
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    // **Gson (Chuyển đổi JSON)**
    implementation ("com.google.code.gson:gson:2.10.1")

    // **Navigation Component (Chuyển màn hình)**
    implementation ("androidx.navigation:navigation-fragment:2.7.6")
    implementation ("androidx.navigation:navigation-ui:2.7.6")

    // **Glide (Load ảnh)**
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // **RecyclerView**
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    // **SharedPreferences (DataStore - thay thế SharedPreferences)**
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // **Lottie (Animation đẹp)**
    implementation ("com.airbnb.android:lottie:6.1.0")

    // **WorkManager (Chạy tác vụ nền)**
    implementation ("androidx.work:work-runtime:2.9.0")

    // **Unit Test**
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //SmoothBottomBar
    implementation ("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")
    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // **Hilt (Dependency Injection)**
    implementation ("com.google.dagger:hilt-android:2.51.1")
    annotationProcessor ("com.google.dagger:hilt-android-compiler:2.51.1") // Dùng annotationProcessor thay cho kapt
    // Coroutines
    implementation  ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
}
