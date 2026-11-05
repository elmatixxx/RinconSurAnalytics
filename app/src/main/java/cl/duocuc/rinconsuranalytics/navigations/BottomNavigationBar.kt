/**
 * ===================================================================================
 * COMPONABLE: BottomNavigationBar
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Barra de navegación inferior basada en Material 3 (NavigationBar).
 * Permite al usuario alternar fluidamente entre las 5 secciones principales de la app
 * (Inicio, Registro, Historial, Analítica y Perfil) manteniendo el estado de navegación.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class NavItem(val label: String, val route: String, val icon: ImageVector)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavItem("Inicio", Screen.Dashboard.route, Icons.Default.Home),
        NavItem("Registro", Screen.Register.route, Icons.Default.AddCircle),
        NavItem("Historial", Screen.History.route, Icons.Default.ListAlt),
        NavItem("Analítica", Screen.Analytics.route, Icons.Default.Analytics),
        NavItem("Perfil", Screen.Profile.route, Icons.Default.Person)
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
