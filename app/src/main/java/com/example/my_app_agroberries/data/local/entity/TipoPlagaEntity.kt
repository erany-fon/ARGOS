package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipos_plaga")
data class TipoPlagaEntity(
    @PrimaryKey
    val idTipoPlaga: Int,
    val nombreTipoPlaga: String,
    val descripcion: String
)