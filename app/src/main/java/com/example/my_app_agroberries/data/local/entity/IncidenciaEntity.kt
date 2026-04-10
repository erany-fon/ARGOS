package com.example.my_app_agroberries.data.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "incidencias",
    foreignKeys = [
        ForeignKey(
        entity = SurcoEntity::class,
        parentColumns = ["idSurco"],
        childColumns = ["idSurco"],
        onDelete = ForeignKey.CASCADE //si se borra el surco se borra la incidencia
    ),
        ForeignKey(
            entity = TipoPlagaEntity::class,
            parentColumns = ["idTipoPlaga"],
            childColumns = ["idTipoPlaga"],
            onDelete = ForeignKey.CASCADE //si se borra el tipo de plaga se borra la incidencia
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE //si se borra el usuario se borra la incidencia
        )
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