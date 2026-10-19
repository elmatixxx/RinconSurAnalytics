# RinconSurAnalytics - Complete Feature Implementation Plan

Implement all 9 screens shown in the UI mockups with full functionality, persistent Room database storage, Bottom Navigation, Biometric Authentication (`BiometricPrompt` for fingerprint / face unlock), and CameraX (`CameraProvider` / `PreviewView` / image capture) for quick sample capture.

## User Review Required

> [!IMPORTANT]
> - **Biometric Authentication**: Uses `androidx.biometric.BiometricPrompt` on supported devices, with fallback to manual login.
> - **CameraX Integration**: Integrated into the Access Detail / Quick Sample screen for real-time camera capture preview.
> - **Room Database**: Fully wired with reactive Flow queries (`AccessDao` and `AccessRepository`) across Dashboard, Register, History, Analytics, and API Sync.

## Proposed Changes

### Dependencies & Configuration
#### [MODIFY] [build.gradle.kts](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/build.gradle.kts)
- Add `androidx.biometric:biometric:1.1.0`.
- Add CameraX dependencies (`androidx.camera:camera-camera2:1.4.1`, `androidx.camera:camera-lifecycle:1.4.1`, `androidx.camera:camera-view:1.4.1`).

### Navigation & UI Structure
#### [MODIFY] [Screen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/navigation/Screen.kt)
- Define routes for all 9 screens: `Login`, `RecoverPassword`, `Dashboard`, `AccessDetail`, `Register`, `History`, `Analytics`, `ApiConfig`, `Profile`.
- Add Bottom Navigation bar component to share across main tabs (`Dashboard`, `Register`, `History`, `Analytics`, `Profile/Config`).

### Feature Screens & ViewModels
#### [NEW] [LoginScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/login/LoginScreen.kt)
- Screen 1: Login UI with RUT/user, password, forgot password navigation, login button, and **Biometric Unlock button** (`BiometricPrompt`).
#### [NEW] [RecoverPasswordScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/recover/RecoverPasswordScreen.kt)
- Screen 2: Password recovery with institutional email input and support card.
#### [MODIFY] [DashboardScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/dashboard/DashboardScreen.kt)
- Screen 3: Main dashboard with flow stats, conversion rate, access status bars, and bottom bar.
#### [NEW] [AccessDetailScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/detail/AccessDetailScreen.kt)
- Screen 4: Detailed access view, physical location, summary, age breakdown, and CameraX preview / "Tomar Muestra Rápida".
#### [MODIFY] [RegisterScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/register/RegisterScreen.kt)
- Screen 5: Sample registration form with Room saving and bottom navigation.
#### [NEW] [HistoryScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/history/HistoryScreen.kt)
- Screen 6: Local Room history with filter tabs (Todos, Tránsito, Compradores) and sync status indicators.
#### [NEW] [AnalyticsScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/analytics/AnalyticsScreen.kt)
- Screen 7: Profile analytics with date range filters, hourly traffic breakdown, and commercial insights.
#### [NEW] [ApiConfigScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/config/ApiConfigScreen.kt)
- Screen 8: API REST configuration, network status, local sync status, manual sync button, and device info.
#### [NEW] [ProfileScreen.kt](file:///C:/Users/m8822/OneDrive/Escritorio/RinconSurAnalytics/app/src/main/java/cl/duocuc/rinconsuranalytics/ui/profile/ProfileScreen.kt)
- Screen 9: User profile, operational assignment, active session token, and logout button returning to login.

## Verification Plan

### Automated Tests
- Run Gradle build and unit tests (`app:assembleDebug`).
- Verify compilation of Room DAOs, CameraX integration, and BiometricPrompt calls.

### Manual Verification
- Deploy to emulator / Android device.
- Test Biometric authentication on login.
- Test full navigation flow across all 9 screens and bottom bar.
- Test sample registration and verification in History (Room DB).
- Test CameraX preview in Access Detail.
