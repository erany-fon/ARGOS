package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
data class RolesEntity(
    @PrimaryKey
    val idRol: Int,
    val nombreRol: String
)