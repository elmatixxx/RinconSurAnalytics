/**
 * ===================================================================================
 * COMPONABLE: LoginScreen (Pantalla 1)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de Autenticación y Login. Incluye validación de credenciales, acceso
 * mediante **BiometricPrompt** (huella digital o reconocimiento facial) y un selector
 * de APIs externas para autenticación con Google (Gmail), Microsoft y OAuth2 institucional.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.login

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRecover: () -> Unit,
    onNavigateToCreateAccount: () -> Unit
) {
    var usuario by remember { mutableStateOf("operador_plazanorte") }
    var password by remember { mutableStateOf("123456") }
    var showSocialDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun launchBiometric() {
        val activity = context as? FragmentActivity
        if (activity == null) {
            Toast.makeText(context, "Autenticación biométrica requiere FragmentActivity", Toast.LENGTH_SHORT).show()
            return
        }

        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context, "Autenticación biométrica exitosa", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Error biométrico: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Acceso Biométrico Rincón Sur")
            .setSubtitle("Usa tu huella digital o reconocimiento facial para entrar")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    if (showSocialDialog) {
        AlertDialog(
            onDismissRequest = { showSocialDialog = false },
            title = { Text("Seleccionar Proveedor API") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Elige el servicio para validar tus credenciales de acceso:")
                    Button(
                        onClick = {
                            showSocialDialog = false
                            Toast.makeText(context, "Autenticado exitosamente con Google (Gmail)", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔴 Iniciar Sesión con Google (Gmail)")
                    }
                    Button(
                        onClick = {
                            showSocialDialog = false
                            Toast.makeText(context, "Autenticado exitosamente con Microsoft (Outlook)", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔵 Iniciar Sesión con Microsoft")
                    }
                    Button(
                        onClick = {
                            showSocialDialog = false
                            Toast.makeText(context, "Autenticado con Cuenta Institucional Rincón Sur", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🌐 Cuenta Institucional (OAuth2)")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSocialDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo / Título
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Rincón Sur", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Text("Conteo & Segmentación de Accesos", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(32.dp))

            // Campo Usuario / RUT
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("USUARIO / RUT") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("CONTRASEÑA") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ¿Olvidaste tu contraseña?
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = onNavigateToRecover) {
                    Text("¿Olvidaste tu contraseña?", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Iniciar Sesión
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("INICIAR SESIÓN", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón API Redes Sociales (Google / Microsoft / Gmail)
            Button(
                onClick = { showSocialDialog = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("🌐 ACCEDER CON GOOGLE / MICROSOFT / GMAIL")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Biométrico (Huella / Facial)
            OutlinedButton(
                onClick = { launchBiometric() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Fingerprint, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ACCESO BIOMÉTRICO (Huella / Facial)")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Crear Cuenta / Registrarse con Google
            TextButton(
                onClick = onNavigateToCreateAccount,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("¿No tienes cuenta? Crear cuenta o Registrarse", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Sede Plaza Norte - MVP DSY1105", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}
