/**
 * ===================================================================================
 * CLASE MODEL: RegisterUiState
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Modelo de estado inmutable de la UI para el registro de muestras y perfil de usuario.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.model

data class RegisterUiState(
    val accesoSeleccionado: Int = 2,
    val esIngreso: Boolean = true,
    val esComprador: Boolean = false,
    val rangoEtario: String = "Joven (<25)",
    val genero: String = "Hombre",
    val numeroAcompanantes: Int = 0,
    val observaciones: String = "",
    val isGuardando: Boolean = false,
    val mensajeExito: String? = null,
    val currentUser: String = "Juan José Pierattini",
    val currentEmail: String = "operador_plazanorte@rinconsur.cl"
)
