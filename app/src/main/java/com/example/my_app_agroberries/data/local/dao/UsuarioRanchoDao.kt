package com.example.my_app_agroberries.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.my_app_agroberries.data.local.entity.UsuarioRanchoEntity

@Dao
interface UsuarioRanchoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(relaciones: List<UsuarioRanchoEntity>)
}