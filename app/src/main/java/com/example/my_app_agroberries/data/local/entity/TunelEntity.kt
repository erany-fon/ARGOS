package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tuneles",
    foreignKeys = [ForeignKey(
        entity = RanchoEntity::class,
        parentColumns = ["idRancho"],
        childColumns = ["idRancho"],
        onDelete = ForeignKey.CASCADE //si se borra rancho, se borra tuneles
    )]
)
data class TunelEntity(
    @PrimaryKey
    val idTunel: Int,
    val numeroTunel: Int,
    val idRancho: Int
)
