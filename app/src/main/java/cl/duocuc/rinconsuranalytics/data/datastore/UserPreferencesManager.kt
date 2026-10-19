/**
 * ===================================================================================
 * CLASE: UserPreferencesManager
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Encargado del almacenamiento local estructurado utilizando Jetpack DataStore (Preferences).
 * Su función es guardar y proveer de forma reactiva (mediante Kotlin Flows) las preferencias
 * y datos de sesión del usuario actual (nombre y correo electrónico) entre aperturas de la app.
 * Cumple con el requisito de almacenamiento local complementario a SQLite.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesManager(private val context: Context) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: "Juan José Pierattini"
        }

    val userEmailFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: "operador_plazanorte@rinconsur.cl"
        }

    suspend fun saveUserSession(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
        }
    }
}
