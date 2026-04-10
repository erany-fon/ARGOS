package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.TunelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TunelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tuneles: List<TunelEntity>)

    @Query("SELECT * FROM tuneles WHERE idRancho = :idRancho ORDER BY numeroTunel ASC")
    fun getTunelesByRancho(idRancho: Int): Flow<List<TunelEntity>>

    @Query("DELETE FROM tuneles")
    suspend fun clearTuneles()
}