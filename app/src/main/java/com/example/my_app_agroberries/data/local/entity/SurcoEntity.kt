package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(
    tableName = "surcos",
    foreignKeys = [ForeignKey(
        entity = TunelEntity::class,
        parentColumns = ["idTunel"],
        childColumns = ["idTunel"],
        onDelete = ForeignKey.CASCADE //si se borra el tunel tambien se borra el surco
    )],
    indices = [Index(value = ["idTunel"])]
)
data class SurcoEntity(
    @PrimaryKey
    val idSurco: Int,
    val numeroSurco: Int,
    val tipoSurco: String,
    val idTunel: Int
)