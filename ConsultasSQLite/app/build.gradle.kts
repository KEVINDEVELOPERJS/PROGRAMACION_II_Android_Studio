// Configuración de plugins para el módulo app
plugins {
    // Plugin para crear la aplicación Android
    alias(libs.plugins.android.application)
}

// Configuración específica de Android para este módulo
android {
    // Habilita el view binding para acceder a las vistas de manera segura
    viewBinding{enable = true}
    // Define el namespace del paquete de la aplicación
    namespace = "com.KevDevJs.consultassqlite"
    // Versión del SDK de Android para compilación
    compileSdk = 35

    // Configuración por defecto de la aplicación
    defaultConfig {
        // Identificador único de la aplicación en Google Play Store
        applicationId = "com.KevDevJs.consultassqlite"
        // Versión mínima de Android requerida para ejecutar la app
        minSdk = 24
        // Versión objetivo de Android para la que se desarrolla la app
        targetSdk = 35
        // Código de versión interno para actualizaciones
        versionCode = 1
        // Nombre de la versión visible para usuarios
        versionName = "1.0"

        // Clase que ejecuta las pruebas instrumentadas
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Configuración de tipos de compilación
    buildTypes {
        // Configuración para la versión de release
        release {
            // Deshabilita la minificación del código
            isMinifyEnabled = false
            // Archivos de reglas para ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // Configuración de opciones de compilación
    compileOptions {
        // Versión de Java para el código fuente
        sourceCompatibility = JavaVersion.VERSION_11
        // Versión de Java para el código compilado
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Dependencias del proyecto
dependencies {
    // Biblioteca de compatibilidad de Android
    implementation(libs.appcompat)
    // Biblioteca de componentes de Material Design
    implementation(libs.material)
    // Biblioteca de componentes de Activity
    implementation(libs.activity)
    // Biblioteca para layouts con restricciones
    implementation(libs.constraintlayout)
    // Biblioteca para pruebas unitarias
    testImplementation(libs.junit)
    // Biblioteca para pruebas de Android
    androidTestImplementation(libs.ext.junit)
    // Biblioteca para pruebas de interfaz de usuario
    androidTestImplementation(libs.espresso.core)
}