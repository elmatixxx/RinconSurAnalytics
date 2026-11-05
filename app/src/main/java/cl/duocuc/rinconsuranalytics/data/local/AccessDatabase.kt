/**
 * ===================================================================================
 * CLASE: AccessDatabase
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Clase abstracta que configura la base de datos local SQLite utilizando Room.
 * Incluye las entidades de accesos y de usuarios (tipos de usuario / registrados).
 * Implementa el patrón Singleton (`getDatabase`).
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AccessEntity::class, UserEntity::class], version = 2, exportSchema = false)
abstract class AccessDatabase : RoomDatabase() {
    abstract fun accessDao(): AccessDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AccessDatabase? = null

        fun getDatabase(context: Context): AccessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccessDatabase::class.java,
                    "rincon_sur_db"
                ).fallbackToDestructiveMigration()
                  .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
