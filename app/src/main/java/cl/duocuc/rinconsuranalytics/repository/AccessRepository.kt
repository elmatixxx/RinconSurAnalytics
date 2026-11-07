/**
 * ===================================================================================
 * CLASE: AccessRepository
 * ===================================================================================
 * DESCRIPCIÓN Y FUNCIÓN EN EL PROYECTO:
 * Actúa como la capa de abstracción de datos (Repository Pattern).
 * Proveer una interfaz limpia entre la base de datos local SQLite (AccessDao, UserDao)
 * y los ViewModels, centralizando inserción, actualización y consulta reactiva de accesos y usuarios.
 * ===================================================================================
 */
package cl.duocuc.rinconsuranalytics.repository

import cl.duocuc.rinconsuranalytics.data.local.AccessDao
import cl.duocuc.rinconsuranalytics.data.local.UserDao
import cl.duocuc.rinconsuranalytics.model.AccessEntity
import cl.duocuc.rinconsuranalytics.model.UserEntity
import kotlinx.coroutines.flow.Flow

class AccessRepository(
    private val accessDao: AccessDao,
    private val userDao: UserDao
) {
    val allAccesses: Flow<List<AccessEntity>> = accessDao.getAllAccesses()
    val allUsers: Flow<List<UserEntity>> = userDao.getAllUsers()

    suspend fun insert(access: AccessEntity) {
        accessDao.insertAccess(access)
    }

    suspend fun update(access: AccessEntity) {
        accessDao.updateAccess(access)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    fun getTransitoCount(accesoId: Int): Flow<Int> {
        return accessDao.getTransitoCountByAccess(accesoId)
    }
}
