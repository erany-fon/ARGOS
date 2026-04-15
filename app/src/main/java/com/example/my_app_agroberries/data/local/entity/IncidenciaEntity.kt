package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(
    tableName = "incidencias",
    foreignKeys = [
        ForeignKey(
        entity = SurcoEntity::class,
        parentColumns = ["idSurco"],
        childColumns = ["idSurco"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = TipoPlagaEntity::class,
            parentColumns = ["idTipoPlaga"],
            childColumns = ["idTipoPlaga"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["idSurco"]),
        Index(value = ["idTipoPlaga"]),
        Index(value = ["idUsuario"])
    ]
)
data class IncidenciaEntity(
    @PrimaryKey(autoGenerate = true)
    val idIncidencia: Int = 0,
    val idSurco: Int,
    val idTipoPlaga: Int,
    val idUsuario: Int,
    val cantidadPlaga: Int,
    val fecha: Long,
    val comentarios: String,
    val fotoUrl: String,
    val sincronizado: Boolean = false
)