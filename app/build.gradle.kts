plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android") // ✅ este es el de Hilt
    kotlin("kapt") // ✅ este es necesario para que compile los @Inject



    //instrucciones de firebase para los servicios de googlee

}

android {
    namespace = "com.example.agendamedica"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.agendamedica"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {


    implementation ("androidx.compose.material:material-icons-extended:1.1.0")
    //Firebase bom
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    //Dependencias del producto de firebase
    implementation("com.google.firebase:firebase-analytics")
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    // Opcional: Si usarás Firestore para almacenar datos de usuarios
    implementation("com.google.firebase:firebase-firestore")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.1")




// Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

// Lifecycle ViewModel para Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

// Coroutines para ViewModel
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")


    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

}

apply(plugin = "com.google.gms.google-services")

//comentario para commit