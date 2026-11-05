/**
 * ===================================================================================
 * CLASE DATA: UserEntity
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Entidad de Room (SQLite) para almacenar los usuarios y operadores registrados en la app
 * (tanto creados por formulario como autenticados mediante Google/Gmail/Microsoft).
 * Cumple con el requisito de persistencia de usuarios en base de datos local SQLite.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios_app")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val email: String,
    val rol: String = "Operador Conteo y Muestreo",
    val timestamp: Long = System.currentTimeMillis()
)
