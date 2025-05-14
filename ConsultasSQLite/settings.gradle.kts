// Configuración de gestión de plugins del proyecto
pluginManagement {
    // Define los repositorios de donde se pueden descargar los plugins
    repositories {
        // Repositorio de Google que contiene las herramientas de desarrollo de Android
        google {
            // Configuración de filtrado de grupos de dependencias
            content {
                // Incluye solo grupos que coincidan con el patrón com.android.*
                includeGroupByRegex("com\\.android.*")
                // Incluye solo grupos que coincidan con el patrón com.google.*
                includeGroupByRegex("com\\.google.*")
                // Incluye solo grupos que coincidan con el patrón androidx.*
                includeGroupByRegex("androidx.*")
            }
        }
        // Repositorio central de Maven para dependencias Java/Kotlin
        mavenCentral()
        // Portal de plugins de Gradle
        gradlePluginPortal()
    }
}

// Configuración de gestión de dependencias del proyecto
dependencyResolutionManagement {
    // Establece el modo de repositorios para fallar si hay conflictos
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    // Define los repositorios para las dependencias del proyecto
    repositories {
        // Repositorio de Google para dependencias de Android
        google()
        // Repositorio central de Maven
        mavenCentral()
    }
}

// Nombre del proyecto raíz
rootProject.name = "ConsultasSQLite"
// Incluye el módulo principal de la aplicación
include(":app")
// Incluye el módulo de base de datos
include(":APP_CONSULT_DB")
