/**
 * ===================================================================================
 * COMPONABLE: DashboardScreen (Pantalla 3)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla principal del Panel de Control (Dashboard). Muestra los flujos totales,
 * tasas de conversión, barras de estado por cada acceso del centro comercial
 * y acceso directo al formulario de registro de muestras.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.ui.navigation.BottomNavigationBar
import cl.duocuc.rinconsuranalytics.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    onNavigateToRegister: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Rincón Sur - Accesos", style = MaterialTheme.typography.titleMedium)
                        Text("28/08/2026 - Turno Tarde", style = MaterialTheme.typography.labelSmall)
                    }
                },
                actions = {
                    AssistChip(
                        onClick = { },
                        label = { Text("Sync") },
                        leadingIcon = { Icon(Icons.Default.CloudSync, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                },
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
            // Fila de Tarjetas Resumen
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ElevatedCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("FLUJO TOTAL", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("4,520", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                        Text("62% Tránsito", style = MaterialTheme.typography.bodySmall)
                    }
                }
                ElevatedCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("CONVERSIÓN", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("38%", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.secondary)
                        Text("1,720 Compras", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Text("ESTADO POR ACCESO", style = MaterialTheme.typography.titleSmall)

            // Acceso 1
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.AccessDetail.route)
                }
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Acceso 1: Conn. Mall Norte", style = MaterialTheme.typography.titleMedium)
                        Text("1,800 pers.", style = MaterialTheme.typography.bodyMedium)
                    }
                    LinearProgressIndicator(progress = { 0.70f }, modifier = Modifier.fillMaxWidth())
                    Text("70% Tránsito / 30% Compra", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                }
            }

            // Acceso 2 (Destacado)
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.AccessDetail.route)
                },
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Acceso 2: Tienda Vecina", style = MaterialTheme.typography.titleMedium)
                        Text("1,500 pers.", style = MaterialTheme.typography.bodyMedium)
                    }
                    LinearProgressIndicator(progress = { 0.82f }, modifier = Modifier.fillMaxWidth())
                    Text("82% Tránsito / 18% Compra", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                }
            }

            // Acceso 3 y 4
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.AccessDetail.route)
                }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Acceso 3 y 4", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Tocar para explorar detalles...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                }
            }

            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("NUEVA MUESTRA DE REGISTRO")
            }
        }
    }
}
