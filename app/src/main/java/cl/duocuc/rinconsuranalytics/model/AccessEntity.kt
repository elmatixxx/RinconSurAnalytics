/**
 * ===================================================================================
 * CLASE MODEL: AccessEntity
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Representa el modelo de datos (Entity) para la tabla SQLite `conteo_accesos` en Room.
 * Almacena los atributos de cada muestra de conteo registrada por el operador.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conteo_accesos")
data class AccessEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val accesoId: Int,                  // 1, 2, 3 o 4
    val esIngreso: Boolean,             // true: Entrada / false: Salida
    val esComprador: Boolean,           // true: Compra / false: Tránsito
    val rangoEtario: String?,           // Joven (<25), Adulto (25-60), Senior (>60)
    val genero: String?,                // Hombre, Mujer, Otro
    val numeroAcompanantes: Int = 0,    // Cantidad de acompañantes
    val observaciones: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
