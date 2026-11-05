/**
 * ===================================================================================
 * INTERFAZ: UserDao (Data Access Object)
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Operaciones SQLite (Room) para la gestión y persistencia de usuarios registrados en la app.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM usuarios_app ORDER BY timestamp DESC")
    fun getAllUsers(): Flow<List<UserEntity>>
}
