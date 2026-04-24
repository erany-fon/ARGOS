package com.example.my_app_agroberries.data.local.entity
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "DetalleIncidencias",
    foreignKeys = [
        ForeignKey(
            entity = IncidenciaEntity::class,
            parentColumns = ["idIncidencia"],
            childColumns = ["idIncidencia"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TipoPlagaEntity::class,
            parentColumns = ["idTipoPlaga"],
            childColumns = ["idTipoPlaga"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlagaEntity::class,
            parentColumns = ["idPlaga"],
            childColumns = ["idPlaga"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idIncidencia"]),
        Index(value = ["idTipoPlaga"]),
        Index(value = ["idPlaga"])
    ]
)
data class DetalleIncidenciasEntity(
    @PrimaryKey(autoGenerate = true)
    val idDetalleIncidencia: Int = 0,
    val idIncidencia: Int,
    val idTipoPlaga: Int,
    val idPlaga: Int,
    val cantidadPlaga: Int
)