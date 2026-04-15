package com.example.my_app_agroberries.data.local.dao


import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.RanchoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface  RanchoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ranchos: List<RanchoEntity>)

    @Query("SELECT * FROM ranchos")
    fun getRanchos(): Flow<List<RanchoEntity>>

    @Query("""
        SELECT r.* FROM ranchos r
        INNER JOIN Usuario_Rancho ur ON r.idRancho = ur.idRancho
        WHERE ur.idUsuario = :idUsuario
    """)
    fun getRanchosByUsuario(idUsuario: Int): Flow<List<RanchoEntity>>

    @Query("DELETE FROM ranchos")
    suspend fun clearRanchos()

}