/**
 * ===================================================================================
 * CLASE: AccessDatabase
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Clase abstracta que configura la base de datos local SQLite utilizando Room.
 * Implementa el patrón Singleton (`getDatabase`) para garantizar que exista una única
 * instancia abierta de la base de datos en toda la aplicación, optimizando recursos y memoria.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AccessEntity::class], version = 1, exportSchema = false)
abstract class AccessDatabase : RoomDatabase() {
    abstract fun accessDao(): AccessDao

    companion object {
        @Volatile
        private var INSTANCE: AccessDatabase? = null

        fun getDatabase(context: Context): AccessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccessDatabase::class.java,
                    "rincon_sur_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
