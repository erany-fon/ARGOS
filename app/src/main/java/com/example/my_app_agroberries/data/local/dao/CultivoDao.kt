package com.example.my_app_agroberries.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.my_app_agroberries.data.local.entity.CultivoEntity

@Dao
interface CultivoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCultivo(cultivo: CultivoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cultivos: List<CultivoEntity>)

    @Query("SELECT * FROM cultivos WHERE idCultivo = :idCultivo")
    suspend fun getCultivoById(idCultivo: Int): CultivoEntity?

    @Query("SELECT * FROM cultivos")
    suspend fun getAllCultivos(): List<CultivoEntity>

    @Query("DELETE FROM cultivos")
    suspend fun clearCultivos()
}

