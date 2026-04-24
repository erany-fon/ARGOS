package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivos")
data class CultivoEntity(
    @PrimaryKey
    val idCultivo: Int,
    val nombreCultivo: String,
    val descripcion: String
)

