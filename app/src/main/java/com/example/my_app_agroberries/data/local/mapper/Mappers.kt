package com.example.my_app_agroberries.data.local.mapper
import com.example.my_app_agroberries.data.local.entity.*
import com.example.my_app_agroberries.domain.model.*

fun UsuarioEntity.toDomain(): Usuario = Usuario(
    idUsuario = idUsuario,
    nombre = nombre,
    apellido = apellido,
    email = email,
    rol = rol
)
fun Usuario.toEntity(hash: String): UsuarioEntity = UsuarioEntity(
    idUsuario = idUsuario,
    nombre = nombre,
    apellido = apellido,
    email = email,
    rol = rol,
    contrasenaHash = hash
)

fun RanchoEntity.toDomain(): Rancho = Rancho(
    idRancho = idRancho,
    nombreRancho = nombreRancho
)

fun Rancho.toEntity(): RanchoEntity = RanchoEntity(
    idRancho = idRancho,
    nombreRancho = nombreRancho
)

fun TunelEntity.toDomain(): Tunel = Tunel(
    idTunel = idTunel,
    numeroTunel = numeroTunel,
    idRancho = idRancho
)

fun Tunel.toEntity(): TunelEntity = TunelEntity(
    idTunel = idTunel,
    numeroTunel = numeroTunel,
    idRancho = idRancho
)

fun SurcoEntity.toDomain(): Surco = Surco(
    idSurco = idSurco,
    numeroSurco = numeroSurco,
    tipoSurco = tipoSurco,
    idTunel = idTunel
)

fun Surco.toEntity(): SurcoEntity = SurcoEntity(
    idSurco = idSurco,
    numeroSurco = numeroSurco,
    tipoSurco = tipoSurco,
    idTunel = idTunel
)

fun TipoPlagaEntity.toDomain(): TipoPlaga = TipoPlaga(
    idTipoPlaga = idTipoPlaga,
    nombreTipoPlaga = nombreTipoPlaga,
    descripcion = descripcion
)

fun TipoPlaga.toEntity(): TipoPlagaEntity = TipoPlagaEntity(
    idTipoPlaga = idTipoPlaga,
    nombreTipoPlaga = nombreTipoPlaga,
    descripcion = descripcion
)

fun IncidenciaEntity.toDomain(): IncidenciaPlaga = IncidenciaPlaga(
    idIncidencia = idIncidencia,
    idSurco = idSurco,
    idTipoPlaga = idTipoPlaga,
    idUsuario = idUsuario,
    cantidadPlaga = cantidadPlaga,
    fecha = fecha,
    comentarios = comentarios,
    fotoUrl = fotoUrl,
    sincronizado = sincronizado
)

fun IncidenciaPlaga.toEntity(): IncidenciaEntity = IncidenciaEntity(
    idIncidencia = idIncidencia,
    idSurco = idSurco,
    idTipoPlaga = idTipoPlaga,
    idUsuario = idUsuario,
    cantidadPlaga = cantidadPlaga,
    fecha = fecha,
    comentarios = comentarios,
    fotoUrl = fotoUrl,
    sincronizado = sincronizado
)