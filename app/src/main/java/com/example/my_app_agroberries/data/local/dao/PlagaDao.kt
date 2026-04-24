package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.PlagaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlagaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaga(plaga: PlagaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plagas: List<PlagaEntity>)

    @Query("SELECT * FROM Plaga WHERE idPlaga = :id")
    suspend fun getPlagaById(id: Int): PlagaEntity?

    @Query("SELECT * FROM Plaga ORDER BY nombrePlaga ASC")
    fun getAllPlagas(): Flow<List<PlagaEntity>>

    @Query("DELETE FROM Plaga")
    suspend fun clearPlagas()
}

