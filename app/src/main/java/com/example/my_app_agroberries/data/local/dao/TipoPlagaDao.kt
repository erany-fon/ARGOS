package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.TipoPlagaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPlagaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tipos: List<TipoPlagaEntity>)

    @Query("SELECT * FROM tipos_plaga ORDER BY nombreTipoPlaga ASC")
    fun getAllTiposPlaga(): Flow<List<TipoPlagaEntity>>

    @Query("DELETE FROM tipos_plaga")
    suspend fun clearTiposPlaga()
}