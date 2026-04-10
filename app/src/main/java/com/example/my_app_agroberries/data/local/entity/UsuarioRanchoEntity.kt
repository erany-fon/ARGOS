package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity

@Entity(
    tableName = "Usuario_Rancho",
    primaryKeys = ["idUsuario", "idRancho"]
)
data class UsuarioRanchoEntity(
    val idUsuario: Int,
    val idRancho: Int
)