/**
 * ===================================================================================
 * COMPONABLE: RegisterScreen (Pantalla 5)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Registro de Muestra. Contiene el formulario validado para capturar
 * datos de conteo (acceso, dirección de flujo, tipo de cliente, edad, género y observaciones).
 * Almacena los datos en la base de datos local SQLite (Room) mediante el ViewModel y
 * limpia automáticamente el formulario tras guardar para evitar duplicados.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.ui.navigation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Muestra") },
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
            // 1. Seleccionar Acceso
            Text("1. Seleccionar Acceso", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..4).forEach { accId ->
                    FilterChip(
                        selected = state.accesoSeleccionado == accId,
                        onClick = { viewModel.onAccesoChanged(accId) },
                        label = { Text("Acc $accId") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 2. Dirección del Flujo
            Text("2. Dirección del Flujo", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.esIngreso,
                    onClick = { viewModel.onDireccionChanged(true) },
                    label = { Text("⤵ Entrada") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = !state.esIngreso,
                    onClick = { viewModel.onDireccionChanged(false) },
                    label = { Text("⤴ Salida") },
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. Tipo de Cliente
            Text("3. Tipo de Cliente", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ElevatedCard(
                    onClick = { viewModel.onTipoClienteChanged(false) },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = if (!state.esComprador) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🚶 Tránsito", style = MaterialTheme.typography.bodyMedium)
                        Text("Sin compra", style = MaterialTheme.typography.labelSmall)
                    }
                }
                ElevatedCard(
                    onClick = { viewModel.onTipoClienteChanged(true) },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = if (state.esComprador) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🛍 Comprador", style = MaterialTheme.typography.labelSmall)
                        Text("Con ticket", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            // 4. Segmento de Edad
            Text("4. Segmento de Edad", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("Joven (<25)", "Adulto (25-60)", "Senior (>60)").forEach { rango ->
                    FilterChip(
                        selected = state.rangoEtario == rango,
                        onClick = { viewModel.onRangoEtarioChanged(rango) },
                        label = { Text(rango, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            // 5. Género Estimado
            Text("5. Género Estimado", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("Hombre", "Mujer", "Otro").forEach { gen ->
                    FilterChip(
                        selected = state.genero == gen,
                        onClick = { viewModel.onGeneroChanged(gen) },
                        label = { Text(gen) }
                    )
                }
            }

            // 6. Acompañantes
            Text("6. Acompañantes", style = MaterialTheme.typography.titleSmall)
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Personas acompañantes:")
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { viewModel.onAcompanantesChanged(-1) }) { Text("-") }
                        Text("${state.numeroAcompanantes}", style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = { viewModel.onAcompanantesChanged(1) }) { Text("+") }
                    }
                }
            }

            // 7. Observaciones
            OutlinedTextField(
                value = state.observaciones,
                onValueChange = { viewModel.onObservacionesChanged(it) },
                label = { Text("Observaciones (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Botón Guardar
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.guardarRegistro()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isGuardando
            ) {
                Text(if (state.isGuardando) "GUARDANDO..." else "GUARDAR REGISTRO")
            }

            state.mensajeExito?.let { mensaje ->
                Snackbar(action = {
                    TextButton(onClick = { viewModel.clearMensaje() }) { Text("OK") }
                }) {
                    Text(mensaje)
                }
            }
        }
    }
}
