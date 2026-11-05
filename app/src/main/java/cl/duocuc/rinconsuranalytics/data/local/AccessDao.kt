/**
 * ===================================================================================
 * INTERFAZ: AccessDao (Data Access Object)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Define las operaciones de base de datos SQLite (Room) para la entidad de accesos.
 * Proporciona métodos suspendidos para insertar y actualizar registros y consultas reactivas
 * mediante Kotlin `Flow` para actualizar automáticamente las listas y estadísticas en la UI.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccess(access: AccessEntity): Unit

    @Update
    suspend fun updateAccess(access: AccessEntity): Unit

    @Query("SELECT * FROM conteo_accesos ORDER BY timestamp DESC")
    fun getAllAccesses(): Flow<List<AccessEntity>>

    @Query("SELECT COUNT(*) FROM conteo_accesos WHERE accesoId = :accesoId AND esComprador = 0")
    fun getTransitoCountByAccess(accesoId: Int): Flow<Int>
}
