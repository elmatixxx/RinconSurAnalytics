/**
 * ===================================================================================
 * COMPONABLE: ProfileScreen (Pantalla 9)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Mi Perfil & Sesión. Muestra de forma reactiva los datos del usuario actual
 * (nombre, correo) obtenidos desde DataStore y ViewModel, la asignación operativa
 * del operador y el botón para cerrar sesión.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.navigations.BottomNavigationBar
import cl.duocuc.rinconsuranalytics.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: RegisterViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tarjeta de Usuario
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(state.currentUser, style = MaterialTheme.typography.titleLarge)
                    Text(state.currentEmail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                    Text("Operador Conteo y Muestreo", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            }

            // Asignación Operativa
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ASIGNACIÓN OPERATIVA", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    Text("• Local: Rincón Sur - Plaza Norte", style = MaterialTheme.typography.bodyMedium)
                    Text("• Accesos: Acceso 1 al 4", style = MaterialTheme.typography.bodyMedium)
                    Text("• Turno: Tarde (12:00 - 20:00 hrs)", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Sesión Activa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("SESIÓN ACTIVA", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
                    Text("Token JWT válido por 8 horas.", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("CERRAR SESIÓN")
                    }
                }
            }
        }
    }
}
