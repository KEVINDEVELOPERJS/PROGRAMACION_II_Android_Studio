// Configuración de plugins necesarios para la aplicación
plugins {
    alias(libs.plugins.android.application)  // Plugin de Android
    alias(libs.plugins.kotlin.android)       // Plugin de Kotlin
}

// Configuración de Android
android {
    // Configuración del namespace y versión del SDK
    namespace = "com.kevinfernandoriverahoyos.appmvvm"  // Namespace de la aplicación
    compileSdk = 35                                      // Versión del SDK para compilar

    // Configuración por defecto de la aplicación
    defaultConfig {
        applicationId = "com.kevinfernandoriverahoyos.appmvvm"  // ID único de la aplicación
        minSdk = 21                                             // Versión mínima de Android soportada
        targetSdk = 35                                          // Versión objetivo de Android
        versionCode = 1                                         // Código de versión interno
        versionName = "1.0"                                     // Nombre de versión visible al usuario

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  // Runner para pruebas
    }

    // Configuración de tipos de build
    buildTypes {
        release {
            isMinifyEnabled = false  // Desactiva la minificación en release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // Configuración de compatibilidad de Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11  // Versión de Java fuente
        targetCompatibility = JavaVersion.VERSION_11  // Versión de Java objetivo
    }
    // Configuración de Kotlin
    kotlinOptions {
        jvmTarget = "11"  // Versión de JVM objetivo
    }
    // Configuración de características de build
    buildFeatures{
        viewBinding = true  // Habilita ViewBinding
    }
}

// Dependencias del proyecto
dependencies {
    // Dependencias básicas de Android
    implementation(libs.androidx.core.ktx)           // Core de Android con Kotlin
    implementation(libs.androidx.appcompat)          // Compatibilidad con versiones anteriores
    implementation(libs.material)                    // Material Design
    implementation(libs.androidx.activity)           // Componentes de Activity
    implementation(libs.androidx.constraintlayout)   // Layout con constraints

    // Dependencias para Fragment
    implementation(libs.fragment.ktx)                // Fragment con Kotlin
    // Dependencias para Activity
    implementation(libs.androidx.activity.ktx)       // Activity con Kotlin
    // Dependencias para ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)  // ViewModel con Kotlin
    // Dependencias para LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)   // LiveData con Kotlin

    // Dependencias para pruebas
    testImplementation(libs.junit)                   // Pruebas unitarias
    androidTestImplementation(libs.androidx.junit)   // Pruebas en Android
    androidTestImplementation(libs.androidx.espresso.core)  // Pruebas de UI
}