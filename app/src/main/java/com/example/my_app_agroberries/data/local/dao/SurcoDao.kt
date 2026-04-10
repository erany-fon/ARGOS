package com.example.my_app_agroberries.data.local.dao
import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.SurcoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurcoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(surcos: List<SurcoEntity>)

    @Query("SELECT * FROM surcos WHERE idTunel = :idTunel ORDER BY numeroSurco ASC")
    fun getSurcosByTunel(idTunel: Int): Flow<List<SurcoEntity>>

    @Query("DELETE FROM surcos")
    suspend fun clearSurcos()
}