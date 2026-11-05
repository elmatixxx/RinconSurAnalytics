/**
 * ===================================================================================
 * COMPONABLE: AnalyticsScreen (Pantalla 7)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Analítica de Perfil. Muestra estadísticas y desgloses de tráfico por rangos
 * horarios (10:00 a 20:00 hrs) y tarjetas de hallazgos comerciales para el análisis
 * del comportamiento de los clientes en los accesos del centro comercial.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.navigations.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    navController: NavController
) {
    var rangoFecha by remember { mutableStateOf("Hoy") }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analítica & Perfil") },
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
            Text("RANGO DE FECHA", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Hoy", "7 Días", "Mes").forEach { r ->
                    FilterChip(
                        selected = rangoFecha == r,
                        onClick = { rangoFecha = r },
                        label = { Text(r) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Tránsito por Rango Horario
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("TRÁNSITO POR RANGO HORARIO", style = MaterialTheme.typography.titleSmall)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("10:00 - 12:00", style = MaterialTheme.typography.bodySmall)
                        Text("300", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.30f }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("12:00 - 14:00", style = MaterialTheme.typography.bodySmall)
                        Text("950", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.95f }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("18:00 - 20:00", style = MaterialTheme.typography.bodySmall)
                        Text("720", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.72f }, modifier = Modifier.fillMaxWidth())
                }
            }

            // Hallazgo Comercial
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Hallazgo Comercial", style = MaterialTheme.typography.titleSmall)
                        Text("El flujo en Acceso 2 se duplica entre 12:00 y 14:00 hrs con fuerte predominio de público joven.", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
