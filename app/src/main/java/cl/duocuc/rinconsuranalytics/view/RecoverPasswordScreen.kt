/**
 * ===================================================================================
 * COMPONABLE: RecoverPasswordScreen (Pantalla 2)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Recuperación de Acceso. Valida el ingreso de correo institucional
 * y muestra información de soporte presencial con el coordinador CITT.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
    onNavigateBack: () -> Unit
) {
    var correo by remember { mutableStateOf("operador@rinconsur.cl") }
    var enviado by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar Clave") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("RESTABLECER CREDENCIALES", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    Text("Ingresa tu correo institucional registrado en el sistema de la tienda.", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text("CORREO INSTITUCIONAL", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    enviado = true
                    Toast.makeText(context, "Instrucciones enviadas a $correo", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("ENVIAR INSTRUCCIONES")
            }

            if (enviado) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Column {
                            Text("✓ Soporte Presencial", style = MaterialTheme.typography.titleSmall)
                            Text("También puedes acudir con el coordinador CITT en Plaza Norte.", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
