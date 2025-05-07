// Configuración de plugins necesarios para la aplicación
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

// Configuración de Android
android {
    // Configuración del namespace y versión del SDK
    namespace = "com.kevinfernandoriverahoyos.appmvvm"  // Namespace de la aplicación
    compileSdk = 35                                   // Versión del SDK para compilar

    // Configuración por defecto de la aplicación
    defaultConfig {
        minSdk = 34                                            // Versión mínima de Android soportada
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  // Runner para pruebas
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8  // Versión de Java fuente
        targetCompatibility = JavaVersion.VERSION_1_8  // Versión de Java objetivo
    }
    // Configuración de Kotlin
    kotlinOptions {
        jvmTarget = "1.8"  // Versión de JVM objetivo
    }
    // Configuración de características de build
    buildFeatures{
        viewBinding = true  // Habilita ViewBinding
    }
}

// Dependencias del proyecto
dependencies {
    // Dependencias básicas de Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")

    // Dependencias para pruebas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}