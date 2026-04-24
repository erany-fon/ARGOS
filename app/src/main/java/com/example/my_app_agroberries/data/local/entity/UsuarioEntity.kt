package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val idUsuario: Int,
    val idRol: Int,
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val usuario: String,
    val email: String, //checar con base de datos
    val passwordHash: String, //guardamos hash
)