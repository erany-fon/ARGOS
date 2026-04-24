package com.example.my_app_agroberries.domain.repository

import com.example.my_app_agroberries.domain.model.Roles
import kotlinx.coroutines.flow.Flow

interface RolesRepository {
    suspend fun insertRol(rol: Roles)

    suspend fun insertAll(roles: List<Roles>)

    suspend fun getRolById(id: Int): Roles?

    fun getAllRoles(): Flow<List<Roles>>

    suspend fun clearRoles()
}

