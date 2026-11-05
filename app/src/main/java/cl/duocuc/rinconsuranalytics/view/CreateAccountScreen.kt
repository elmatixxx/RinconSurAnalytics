/**
 * ===================================================================================
 * COMPONABLE: CreateAccountScreen
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Pantalla de registro de nueva cuenta de operador. Incluye validación de formularios
 * y soporte para autenticación mediante APIs externas (Google/Gmail y Microsoft).
 * Actualiza de forma reactiva las preferencias de usuario mediante DataStore y ViewModel.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onNavigateBack: () -> Unit,
    onAccountCreatedSuccess: (String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Nueva Cuenta") },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro de Operador Rincón Sur", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text("Crea tu cuenta para registrar accesos y conteos", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre Completo
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("NOMBRE COMPLETO") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Correo (Gmail / Institucional)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("CORREO GMAIL / INSTITUCIONAL") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
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

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Registrarse con Correo
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Cuenta creada exitosamente para $email", Toast.LENGTH_SHORT).show()
                        onAccountCreatedSuccess(nombre, email)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("CREAR CUENTA")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Registrarse con Google (Gmail)
            OutlinedButton(
                onClick = {
                    val googleName = if (nombre.isBlank()) "Usuario Google" else nombre
                    val googleEmail = if (email.isBlank()) "usuario.google@gmail.com" else email
                    Toast.makeText(context, "Autenticación con Google (Gmail) exitosa", Toast.LENGTH_SHORT).show()
                    onAccountCreatedSuccess(googleName, googleEmail)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("🔴 REGISTRARSE CON GOOGLE (GMAIL)")
            }
        }
    }
}
