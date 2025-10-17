plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

// 👈 Asegura la versión del plugin
}

android {
    namespace = "com.example.cityguide"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cityguide"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
    }

    // ✅ Activa Jetpack Compose
    buildFeatures {
        compose = true
    }

    // ✅ Configura el Compose Compiler compatible con Kotlin 2.0+
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    // ✅ Configura correctamente la versión de JVM
    kotlinOptions {
        jvmTarget = "17"
    }

    // ✅ Alinea Java y Kotlin al mismo nivel de compatibilidad
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // ✅ Usa el toolchain oficial de Kotlin (recomendado)
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // BOM (para sincronizar versiones de Compose)
    val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Compose UI
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")

    // Navegación
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // ViewModel + Runtime Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

    // Material Components (para temas XML)
    implementation("com.google.android.material:material:1.12.0")

    // Íconos de Material Design extendidos
    implementation("androidx.compose.material:material-icons-extended")

    // Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
