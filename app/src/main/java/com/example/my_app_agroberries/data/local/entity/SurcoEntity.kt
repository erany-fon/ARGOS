package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(
    tableName = "surcos",
    foreignKeys = [
        ForeignKey(
            entity = TunelEntity::class,
            parentColumns = ["idTunel"],
            childColumns = ["idTunel"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CultivoEntity::class,
            parentColumns = ["idCultivo"],
            childColumns = ["idCultivo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idTunel"]), Index(value = ["idCultivo"])]
)
data class SurcoEntity(
    @PrimaryKey
    val idSurco: Int,
    val idTunel: Int,
    val idCultivo: Int,
    val numeroSurco: Int,
    val cultivo: String,
)