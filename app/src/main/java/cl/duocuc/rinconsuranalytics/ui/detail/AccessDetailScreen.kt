/**
 * ===================================================================================
 * COMPONABLE: AccessDetailScreen (Pantalla 4)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Detalle por Acceso. Integra el recurso nativo **CameraX** para mostrar
 * una vista previa de la cámara en tiempo real (PreviewView), junto con resúmenes
 * de tráfico, ubicación física y desgloses etarios.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.detail

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scrollState = rememberScrollState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (!granted) {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acceso 2: Tienda Vecina") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
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
            // Ubicación Física
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("UBICACIÓN FÍSICA", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    Text("Paso peatonal entre Mall y Tienda Mejoramiento del Hogar.", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Resumen de Hoy
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("RESUMEN DE HOY", style = MaterialTheme.typography.titleSmall)
                    Text("• Registros Totales: 1,500", style = MaterialTheme.typography.bodyMedium)
                    Text("• Tránsito Peatonal: 1,230 (82%)", style = MaterialTheme.typography.bodyMedium)
                    Text("• Clientes Compradores: 270 (18%)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                }
            }

            // Desglos Etario en Tránsito
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("DESGLOSE ETARIO EN TRÁNSITO", style = MaterialTheme.typography.titleSmall)
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Jóvenes (<25)", style = MaterialTheme.typography.bodySmall)
                        Text("60%", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.60f }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Adultos (25-60)", style = MaterialTheme.typography.bodySmall)
                        Text("30%", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.30f }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Seniors (>60)", style = MaterialTheme.typography.bodySmall)
                        Text("10%", style = MaterialTheme.typography.bodySmall)
                    }
                    LinearProgressIndicator(progress = { 0.10f }, modifier = Modifier.fillMaxWidth())
                }
            }

            // CámaraX Preview Card
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("CÁMARA EN VIVO - RECONOCIMIENTO / MUESTRA", style = MaterialTheme.typography.titleSmall)
                    if (hasCameraPermission) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            AndroidView(
                                factory = { ctx ->
                                    val previewView = PreviewView(ctx)
                                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                                    cameraProviderFuture.addListener({
                                        val cameraProvider = cameraProviderFuture.get()
                                        val preview = Preview.Builder().build().also {
                                            it.setSurfaceProvider(previewView.surfaceProvider)
                                        }
                                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                                        try {
                                            cameraProvider.unbindAll()
                                            cameraProvider.bindToLifecycle(
                                                lifecycleOwner, cameraSelector, preview
                                            )
                                        } catch (exc: Exception) {
                                            // Handle exception
                                        }
                                    }, ContextCompat.getMainExecutor(ctx))
                                    previewView
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        Text("Se requiere permiso de cámara para ver la vista previa.", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            // Botón Tomar Muestra Rápida
            Button(
                onClick = {
                    Toast.makeText(context, "Capturando muestra con CameraX...", Toast.LENGTH_SHORT).show()
                    onNavigateToRegister()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tomar Muestra Rápida")
            }
        }
    }
}
