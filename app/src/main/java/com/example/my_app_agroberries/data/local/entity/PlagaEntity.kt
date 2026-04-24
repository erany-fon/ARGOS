package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Plaga")
data class PlagaEntity(
    @PrimaryKey(autoGenerate = true)
    val idPlaga: Int,
    val nombrePlaga: String,
    val descripcion: String
)