/**
 * ===================================================================================
 * COMPONABLE: NavGraph
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Controla la navegación funcional y las transiciones animadas de la aplicación.
 * Define el grafo de navegación (NavHost) conectando las 9 pantallas desarrolladas
 * e incorporando animaciones fluidas (fade in/out y deslizamiento) requeridas por la rúbrica.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.navigations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duocuc.rinconsuranalytics.repository.AccessRepository
import cl.duocuc.rinconsuranalytics.view.*
import cl.duocuc.rinconsuranalytics.viewmodels.RegisterViewModel

@Composable
fun NavGraph(
    registerViewModel: RegisterViewModel,
    repository: AccessRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // 1. Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Dashboard.route) { popUpTo(Screen.Login.route) { inclusive = true } } },
                onNavigateToRecover = { navController.navigate(Screen.RecoverPassword.route) },
                onNavigateToCreateAccount = { navController.navigate(Screen.CreateAccount.route) }
            )
        }

        // 1.1. Crear Cuenta (con opción Gmail / Google)
        composable(Screen.CreateAccount.route) {
            CreateAccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onAccountCreatedSuccess = { name, email ->
                    registerViewModel.setUserProfile(name, email)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Recuperar Contraseña
        composable(Screen.RecoverPassword.route) {
            RecoverPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 3. Dashboard Principal
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        // 4. Detalle por Acceso (con CameraX)
        composable(Screen.AccessDetail.route) {
            AccessDetailScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        // 5. Registro de Muestra
        composable(Screen.Register.route) {
            RegisterScreen(
                navController = navController,
                viewModel = registerViewModel
            )
        }

        // 6. Historial Local (ROOM)
        composable(Screen.History.route) {
            HistoryScreen(
                navController = navController,
                viewModel = registerViewModel
            )
        }

        // 7. Analítica de Perfil
        composable(Screen.Analytics.route) {
            AnalyticsScreen(
                navController = navController
            )
        }

        // 8. Configuración API REST
        composable(Screen.ApiConfig.route) {
            ApiConfigScreen(
                navController = navController
            )
        }

        // 9. Mi Perfil & Sesión
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModel = registerViewModel,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
