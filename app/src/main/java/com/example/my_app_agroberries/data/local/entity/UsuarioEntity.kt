package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasenaHash: String, //guardamos hash
    val rol: String
)