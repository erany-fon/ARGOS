package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranchos")
data class RanchoEntity(
    @PrimaryKey
    val idRancho: Int,
    val nombreRancho: String
)