/**
 * ===================================================================================
 * CLASE: RegisterViewModel
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * ViewModel de la arquitectura MVVM. Gestiona la lógica de negocio y el estado de la UI
 * mediante `StateFlow`. Conecta la UI con el repositorio (AccessRepository) y DataStore
 * (UserPreferencesManager) para insertar registros en SQLite y persistir la sesión de usuario
 * de forma reactiva y segura ante cambios de configuración (rotación de pantalla).
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.rinconsuranalytics.data.datastore.UserPreferencesManager
import cl.duocuc.rinconsuranalytics.data.local.AccessEntity
import cl.duocuc.rinconsuranalytics.data.repository.AccessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AccessRepository,
    private val preferencesManager: UserPreferencesManager? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {
        preferencesManager?.let { prefs ->
            viewModelScope.launch {
                prefs.userNameFlow.collect { name ->
                    _uiState.update { it.copy(currentUser = name) }
                }
            }
            viewModelScope.launch {
                prefs.userEmailFlow.collect { email ->
                    _uiState.update { it.copy(currentEmail = email) }
                }
            }
        }
    }

    fun onAccesoChanged(accesoId: Int) {
        _uiState.update { it.copy(accesoSeleccionado = accesoId) }
    }

    fun onDireccionChanged(esIngreso: Boolean) {
        _uiState.update { it.copy(esIngreso = esIngreso) }
    }

    fun onTipoClienteChanged(esComprador: Boolean) {
        _uiState.update { it.copy(esComprador = esComprador) }
    }

    fun onRangoEtarioChanged(rango: String) {
        _uiState.update { it.copy(rangoEtario = rango) }
    }

    fun onGeneroChanged(genero: String) {
        _uiState.update { it.copy(genero = genero) }
    }

    fun onAcompanantesChanged(delta: Int) {
        _uiState.update {
            val nuevoValor = (it.numeroAcompanantes + delta).coerceAtLeast(0)
            it.copy(numeroAcompanantes = nuevoValor)
        }
    }

    fun onObservacionesChanged(texto: String) {
        _uiState.update { it.copy(observaciones = texto) }
    }

    fun setUserProfile(name: String, email: String) {
        _uiState.update { it.copy(currentUser = name.ifBlank { "Operador Rincón Sur" }, currentEmail = email.ifBlank { "operador@rinconsur.cl" }) }
        preferencesManager?.let { prefs ->
            viewModelScope.launch {
                prefs.saveUserSession(name, email)
            }
        }
    }

    fun guardarRegistro() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGuardando = true) }

            val entidad = AccessEntity(
                accesoId = _uiState.value.accesoSeleccionado,
                esIngreso = _uiState.value.esIngreso,
                esComprador = _uiState.value.esComprador,
                rangoEtario = _uiState.value.rangoEtario,
                genero = _uiState.value.genero,
                numeroAcompanantes = _uiState.value.numeroAcompanantes,
                observaciones = _uiState.value.observaciones.ifBlank { null }
            )

            repository.insert(entidad)

            // Vaciamos los campos de texto y acompañantes para evitar duplicados
            _uiState.update {
                it.copy(
                    isGuardando = false,
                    numeroAcompanantes = 0,
                    observaciones = "",
                    mensajeExito = "Muestra guardada y formulario restablecido correctamente"
                )
            }
        }
    }

    fun clearMensaje() {
        _uiState.update { it.copy(mensajeExito = null) }
    }
}
