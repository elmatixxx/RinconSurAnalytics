/**
 * ===================================================================================
 * COMPONABLE: ApiConfigScreen (Pantalla 8)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Configuración de API REST y Estado de Red. Muestra el estado de conexión
 * con el backend de microservicios, el estado de sincronización con la base de datos local
 * (Room) y permite forzar la sincronización manual de datos.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.navigations.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiConfigScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var isSyncing by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración API") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado de Red
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ESTADO DE RED", style = MaterialTheme.typography.titleSmall)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Badge(containerColor = MaterialTheme.colorScheme.primary)
                        Text("Conectado Red Mall", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text("Backend: Spring Boot Microservices", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                }
            }

            // Sincronización Local (Room)
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("SINCRONIZACIÓN LOCAL (ROOM)", style = MaterialTheme.typography.titleSmall)
                    Text("• Pendientes: 1 registro", style = MaterialTheme.typography.bodyMedium)
                    Text("• Última sincro: 14:30 hrs", style = MaterialTheme.typography.bodyMedium)

                    Button(
                        onClick = {
                            isSyncing = true
                            Toast.makeText(context, "Sincronizando con API REST...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSyncing
                    ) {
                        Text(if (isSyncing) "SINCRONIZANDO..." else "FORZAR SINCRONIZACIÓN")
                    }
                }
            }

            // Datos del Dispositivo
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("DATOS DEL DISPOSITIVO", style = MaterialTheme.typography.titleSmall)
                    Text("Terminal: Android POS #04", style = MaterialTheme.typography.bodyMedium)
                    Text("App Version: 1.0.2 MVP", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
