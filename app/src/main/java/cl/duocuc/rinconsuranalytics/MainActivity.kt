/**
 * ===================================================================================
 * CLASE: MainActivity
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Es el punto de entrada (Entry Point) principal de la aplicación Android.
 * Su rol es inicializar los componentes estructurales de la arquitectura MVVM:
 * 1. Habilita el diseño Edge-to-Edge para las barras del sistema (Material 3).
 * 2. Inicializa la base de datos local SQLite mediante Jetpack Room (AccessDatabase).
 * 3. Configura el repositorio (AccessRepository) y el DataStore de preferencias (UserPreferencesManager).
 * 4. Instancia el ViewModel (RegisterViewModel) y carga el árbol de navegación (NavGraph).
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duocuc.rinconsuranalytics.data.datastore.UserPreferencesManager
import cl.duocuc.rinconsuranalytics.data.local.AccessDatabase
import cl.duocuc.rinconsuranalytics.data.repository.AccessRepository
import cl.duocuc.rinconsuranalytics.ui.navigation.NavGraph
import cl.duocuc.rinconsuranalytics.ui.register.RegisterViewModel
import cl.duocuc.rinconsuranalytics.ui.theme.RinconSurAnalyticsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización de Room (SQLite), DataStore y ViewModel (MVVM)
        val database = AccessDatabase.getDatabase(this)
        val repository = AccessRepository(database.accessDao())
        val preferencesManager = UserPreferencesManager(this)
        val registerViewModel = RegisterViewModel(repository, preferencesManager)

        setContent {
            RinconSurAnalyticsTheme {
                NavGraph(registerViewModel = registerViewModel, repository = repository)
            }
        }
    }
}
