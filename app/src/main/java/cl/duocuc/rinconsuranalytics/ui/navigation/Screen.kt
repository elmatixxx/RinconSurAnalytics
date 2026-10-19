/**
 * ===================================================================================
 * CLASE: Screen (Sealed Class)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Define de manera segura y centralizada todas las rutas de navegación (Screens)
 * disponibles en la aplicación para Jetpack Navigation Compose.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object RecoverPassword : Screen("recover_password")
    object Dashboard : Screen("dashboard")
    object AccessDetail : Screen("access_detail")
    object Register : Screen("register")
    object History : Screen("history")
    object Analytics : Screen("analytics")
    object ApiConfig : Screen("api_config")
    object Profile : Screen("profile")
}
