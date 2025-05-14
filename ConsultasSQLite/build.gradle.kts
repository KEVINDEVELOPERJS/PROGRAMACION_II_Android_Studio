// Archivo de configuración principal de Gradle para el proyecto
// Este archivo define la configuración común que se aplica a todos los módulos del proyecto

// Sección de plugins que define las herramientas necesarias para el proyecto
plugins {
    // Plugin para crear aplicaciones Android
    // Este plugin proporciona las tareas y configuraciones necesarias para compilar aplicaciones Android
    alias(libs.plugins.android.application) apply false
    
    // Plugin para crear bibliotecas Android
    // Este plugin permite crear módulos de biblioteca reutilizables
    alias(libs.plugins.android.library) apply false
}