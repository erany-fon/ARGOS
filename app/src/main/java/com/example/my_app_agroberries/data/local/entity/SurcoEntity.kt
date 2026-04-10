package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "surcos",
    foreignKeys = [ForeignKey(
        entity = TunelEntity::class,
        parentColumns = ["idTunel"],
        childColumns = ["idTunel"],
        onDelete = ForeignKey.CASCADE //si se borra el tunel se borra el surco
    )]
)
data class SurcoEntity(
    @PrimaryKey
    val idSurco: Int,
    val numeroSurco: Int,
    val tipoSurco: String,
    val idTunel: Int
)