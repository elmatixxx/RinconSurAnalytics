# Arquitectura y Estructura del Proyecto - Evaluacion Parcial 2 (DSY1105)

Este documento detalla la estructura de paquetes y cómo el proyecto **RinconSurAnalytics** cumple con cada uno de los criterios de la rúbrica de la **Evaluación Parcial 2 (Duoc UC)**.

## 📁 Estructura de Carpetas (MVVM)

```text
cl.duocuc.rinconsuranalytics/
│
├── MainActivity.kt                      # Actividad principal (Entry Point)
│
├── data/
│   ├── datastore/
│   │   └── UserPreferencesManager.kt    # Almacenamiento con Jetpack DataStore
│   └── local/
│       ├── AccessDao.kt                 # DAO de SQLite (Room)
│       ├── AccessDatabase.kt            # Base de datos SQLite (Room)
│       └── AccessEntity.kt              # Entidad de datos locales
│
├── repository/
│   └── AccessRepository.kt              # Repositorio central de datos
│
└── ui/
    ├── analytics/
    │   └── AnalyticsScreen.kt           # Pantalla 7: Analítica de Perfil
    ├── config/
    │   └── ApiConfigScreen.kt           # Pantalla 8: Configuración API REST
    ├── dashboard/
    │   └── DashboardScreen.kt           # Pantalla 3: Dashboard Principal
    ├── detail/
    │   └── AccessDetailScreen.kt        # Pantalla 4: Detalle por Acceso (CameraX)
    ├── history/
    │   └── HistoryScreen.kt             # Pantalla 6: Historial Local (Room)
    ├── login/
    │   ├── CreateAccountScreen.kt       # Pantalla de Creación de Cuenta / Google
    │   └── LoginScreen.kt               # Pantalla 1: Autenticación con Biometría
    ├── navigation/
    │   ├── BottomNavigationBar.kt       # Barra de Navegación Inferior M3
    │   ├── NavGraph.kt                  # Grafo de Navegación con Animaciones
    │   └── Screen.kt                    # Definición de Rutas
    ├── profile/
    │   └── ProfileScreen.kt             # Pantalla 9: Mi Perfil & Sesión
    ├── recover/
    │   └── RecoverPasswordScreen.kt     # Pantalla 2: Recuperación de Clave
    ├── register/
    │   ├── RegisterScreen.kt            # Pantalla 5: Registro de Muestra
    │   ├── RegisterUiState.kt           # Estado UI (StateFlow)
    │   └── RegisterViewModel.kt         # ViewModel MVVM
    └── theme/
        ├── Color.kt                     # Paleta de Colores Material 3
        ├── Theme.kt                     # Tema Material 3
        └── Type.kt                      # Tipografía Material 3
```

---

## ✅ Cumplimiento de la Rúbrica (Ev. Parcial 2)

| Criterio de Rúbrica | Implementación en el Proyecto | Archivos Clave |
| :--- | :--- | :--- |
| **Diseño visual con Material 3** | Colores, tipografías y componentes adaptados a Material 3 (`Scaffold`, `TopAppBar`, `NavigationBar`, `ElevatedCard`). | `Theme.kt`, `Color.kt`, `Type.kt` |
| **Formularios validados** | Validación de campos vacíos y formatos en Login, Registro de Cuenta, Recuperación de Clave y Formulario de Muestras. | `LoginScreen.kt`, `CreateAccountScreen.kt`, `RegisterScreen.kt` |
| **Navegación funcional** | 9 pantallas conectadas mediante Jetpack Navigation Compose con barra de navegación inferior compartida. | `NavGraph.kt`, `Screen.kt`, `BottomNavigationBar.kt` |
| **Gestión de estado** | Uso de ViewModels con `MutableStateFlow` y `StateFlow` para reactividad y persistencia de sesión. | `RegisterViewModel.kt`, `RegisterUiState.kt` |
| **Almacenamiento local** | **SQLite** mediante Jetpack Room (`AccessDatabase`) y **DataStore** (`UserPreferencesManager`). | `AccessDatabase.kt`, `UserPreferencesManager.kt` |
| **Uso de recursos nativos** | **BiometricPrompt** (huella digital y reconocimiento facial) y **CameraX** (vista previa y captura en vivo). | `LoginScreen.kt`, `AccessDetailScreen.kt` |
| **Animaciones** | Transiciones animadas de entrada y salida (`fadeIn`, `fadeOut`, `slideIntoContainer`) en el grafo de navegación. | `NavGraph.kt` |
