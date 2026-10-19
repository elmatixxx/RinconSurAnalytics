/**
 * ===================================================================================
 * CLASE: AccessRepository
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Actúa como la capa de abstracción de datos (Repository Pattern).
 * Su función es proveer una interfaz limpia entre la base de datos local (AccessDao)
 * y los ViewModels, centralizando las operaciones de inserción y consulta reactiva de accesos.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.data.repository

import cl.duocuc.rinconsuranalytics.data.local.AccessDao
import cl.duocuc.rinconsuranalytics.data.local.AccessEntity
import kotlinx.coroutines.flow.Flow

class AccessRepository(private val accessDao: AccessDao) {
    val allAccesses: Flow<List<AccessEntity>> = accessDao.getAllAccesses()

    suspend fun insert(access: AccessEntity) {
        accessDao.insertAccess(access)
    }

    fun getTransitoCount(accesoId: Int): Flow<Int> {
        return accessDao.getTransitoCountByAccess(accesoId)
    }
}
