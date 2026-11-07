/**
 * ===================================================================================
 * COMPONABLE: HistoryScreen (Pantalla 6)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Historial Local. Consume reactivamente los datos almacenados en la base
 * de datos SQLite (Room) mediante `Flow` y `collectAsState`, permitiendo filtrar
 * registros por tipo (Todos, Tránsito, Compradores), ver detalles completos y
 * editar cualquier registro (acompañantes, género, observaciones, etc.) usando el botón de editar.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duocuc.rinconsuranalytics.model.AccessEntity
import cl.duocuc.rinconsuranalytics.navigations.BottomNavigationBar
import cl.duocuc.rinconsuranalytics.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val accesses by viewModel.allAccesses.collectAsState(initial = emptyList())
    var filtro by remember { mutableStateOf("Todos") }
    var editingAccess by remember { mutableStateOf<AccessEntity?>(null) }

    val filteredList = accesses.filter { access ->
        when (filtro) {
            "Tránsito" -> !access.esComprador
            "Compradores" -> access.esComprador
            else -> true
        }
    }

    // Diálogo de Edición
    editingAccess?.let { access ->
        var generoEdit by remember { mutableStateOf(access.genero ?: "Hombre") }
        var acompanantesEdit by remember { mutableStateOf(access.numeroAcompanantes) }
        var obsEdit by remember { mutableStateOf(access.observaciones ?: "") }
        var esCompradorEdit by remember { mutableStateOf(access.esComprador) }

        AlertDialog(
            onDismissRequest = { editingAccess = null },
            title = { Text("Editar Registro de Muestra") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Acceso #${access.accesoId}", style = MaterialTheme.typography.titleSmall)

                    // Tipo de Cliente
                    Text("Tipo de Cliente:", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = !esCompradorEdit,
                            onClick = { esCompradorEdit = false },
                            label = { Text("Tránsito") }
                        )
                        FilterChip(
                            selected = esCompradorEdit,
                            onClick = { esCompradorEdit = true },
                            label = { Text("Comprador") }
                        )
                    }

                    // Género
                    Text("Género:", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Hombre", "Mujer", "Otro").forEach { g ->
                            FilterChip(
                                selected = generoEdit == g,
                                onClick = { generoEdit = g },
                                label = { Text(g) }
                            )
                        }
                    }

                    // Acompañantes
                    Text("Acompañantes: $acompanantesEdit", style = MaterialTheme.typography.labelMedium)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { if (acompanantesEdit > 0) acompanantesEdit-- }) { Text("-") }
                        Text("$acompanantesEdit", style = MaterialTheme.typography.titleMedium)
                        Button(onClick = { acompanantesEdit++ }) { Text("+") }
                    }

                    // Observaciones
                    OutlinedTextField(
                        value = obsEdit,
                        onValueChange = { obsEdit = it },
                        label = { Text("Observaciones") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = access.copy(
                            genero = generoEdit,
                            numeroAcompanantes = acompanantesEdit,
                            observaciones = obsEdit.ifBlank { null },
                            esComprador = esCompradorEdit
                        )
                        viewModel.updateAccess(updated)
                        editingAccess = null
                    }
                ) {
                    Text("GUARDAR CAMBIOS")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingAccess = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial Local (ROOM)") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
                    }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filtros
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todos", "Tránsito", "Compradores").forEach { f ->
                    FilterChip(
                        selected = filtro == f,
                        onClick = { filtro = f },
                        label = { Text(f) }
                    )
                }
            }

            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay registros locales guardados.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredList) { access ->
                        val tipoStr = if (access.esComprador) "🛍 Comprador" else "🚶 Tránsito (${access.rangoEtario})"
                        val direccionStr = if (access.esIngreso) "⤵ Entrada" else "⤴ Salida"
                        val fechaStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(access.timestamp))

                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Acceso ${access.accesoId} - $tipoStr", style = MaterialTheme.typography.titleMedium)
                                    
                                    // Botón de Edición Visible con Texto e Icono
                                    OutlinedButton(
                                        onClick = { editingAccess = access },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Editar")
                                    }
                                }

                                Text("• Dirección: $direccionStr • Género: ${access.genero ?: "N/A"}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("• Acompañantes: ${access.numeroAcompanantes} persona(s)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                
                                if (!access.observaciones.isNullOrBlank()) {
                                    Text("• Obs: \"${access.observaciones}\"", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                }

                                Text(fechaStr, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                            }
                        }
                    }
                }
            }
        }
    }
}
