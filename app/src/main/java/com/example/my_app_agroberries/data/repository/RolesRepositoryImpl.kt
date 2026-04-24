package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.RolesDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Roles
import com.example.my_app_agroberries.domain.repository.RolesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RolesRepositoryImpl @Inject constructor(
    private val rolesDao: RolesDao
) : RolesRepository {

    override suspend fun insertRol(rol: Roles) {
        rolesDao.insertRol(rol.toEntity())
    }

    override suspend fun insertAll(roles: List<Roles>) {
        rolesDao.insertAll(roles.map { it.toEntity() })
    }

    override suspend fun getRolById(id: Int): Roles? {
        return rolesDao.getRolById(id)?.toDomain()
    }

    override fun getAllRoles(): Flow<List<Roles>> {
        return rolesDao.getAllRoles().map { roles ->
            roles.map { it.toDomain() }
        }
    }

    override suspend fun clearRoles() {
        rolesDao.clearRoles()
    }
}
