package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.RolesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RolesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRol(rol: RolesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(roles: List<RolesEntity>)

    @Query("SELECT * FROM roles WHERE idRol = :id")
    suspend fun getRolById(id: Int): RolesEntity?

    @Query("SELECT * FROM roles ORDER BY nombreRol ASC")
    fun getAllRoles(): Flow<List<RolesEntity>>

    @Query("DELETE FROM roles")
    suspend fun clearRoles()
}

