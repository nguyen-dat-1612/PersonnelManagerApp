plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.managerapp.personnelmanagerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.managerapp.personnelmanagerapp"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    // Core Android (from libs)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // AndroidX & Lifecycle
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.security:security-crypto:1.0.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

    // Retrofit & Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.core.ktx)  // For Kotlin extensions
    annotationProcessor ("androidx.room:room-compiler:2.6.1")  // Use 'annotationProcessor' for Java

    // Glide (Image Loading)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")  // Use 'annotationProcessor' for Java

    // Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:2.51.1")
    annotationProcessor ("com.google.dagger:hilt-android-compiler:2.51.1")  // Use 'annotationProcessor' for Java

    // RxJava3 support for Room
    implementation ("androidx.room:room-rxjava3:2.6.1")
    // RxJava 3
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    // Firebase (using BOM)
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // UI Libraries
     implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Lottie Animation
    implementation("com.airbnb.android:lottie:6.1.0")

    // JWT
    implementation("com.auth0:java-jwt:3.19.2")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // MPAndroidChart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // TimelineView
    implementation ("com.github.vipulasri:timelineview:1.2.2")

    //Web Socket
    implementation ("org.java-websocket:Java-WebSocket:1.5.2")
    implementation ("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")
    implementation ("com.google.code.gson:gson:2.10.1")

    //
    implementation ("com.google.android.flexbox:flexbox:3.0.0")

}
