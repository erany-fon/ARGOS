package com.example.my_app_agroberries.data.local.mapper
import com.example.my_app_agroberries.data.local.entity.*
import com.example.my_app_agroberries.domain.model.*

fun RolesEntity.toDomain(): Roles = Roles(
    idRol = idRol,
    nombreRol = nombreRol
)

fun Roles.toEntity(): RolesEntity = RolesEntity(
    idRol = idRol,
    nombreRol = nombreRol
)

fun UsuarioEntity.toDomain(): Usuario = Usuario(
    idUsuario = idUsuario,
    idRol = idRol,
    nombre = nombre,
    apellidoP = apellidoP,
    apellidoM = apellidoM,
    email = email,
    usuario = usuario
)
fun Usuario.toEntity(hash: String): UsuarioEntity = UsuarioEntity(
    idUsuario = idUsuario,
    idRol = idRol,
    nombre = nombre,
    apellidoP = apellidoP,
    apellidoM = apellidoM,
    email = email,
    usuario = usuario,
    passwordHash = hash
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

fun CultivoEntity.toDomain(): Cultivo = Cultivo(
    idCultivo = idCultivo,
    nombreCultivo = nombreCultivo,
    descripcion = descripcion
)

fun Cultivo.toEntity(): CultivoEntity = CultivoEntity(
    idCultivo = idCultivo,
    nombreCultivo = nombreCultivo,
    descripcion = descripcion
)

fun SurcoEntity.toDomain(): Surco = Surco(
    idSurco = idSurco,
    numeroSurco = numeroSurco,
    idTunel = idTunel,
    idCultivo = idCultivo,
    cultivo = cultivo
)

fun Surco.toEntity(): SurcoEntity = SurcoEntity(
    idSurco = idSurco,
    numeroSurco = numeroSurco,
    idTunel = idTunel,
    idCultivo = idCultivo,
    cultivo = cultivo
)

fun PlagaEntity.toDomain(): Plaga = Plaga(
    idPlaga = idPlaga,
    nombrePlaga = nombrePlaga,
    descripcion = descripcion
)

fun Plaga.toEntity(): PlagaEntity = PlagaEntity(
    idPlaga = idPlaga,
    nombrePlaga = nombrePlaga,
    descripcion = descripcion
)

fun TipoPlagaEntity.toDomain(): TipoPlaga = TipoPlaga(
    idTipoPlaga = idTipoPlaga,
    idPlaga = idPlaga,
    nombreTipoPlaga = nombreTipoPlaga,
    descripcion = descripcion
)

fun TipoPlaga.toEntity(): TipoPlagaEntity = TipoPlagaEntity(
    idTipoPlaga = idTipoPlaga,
    idPlaga = idPlaga,
    nombreTipoPlaga = nombreTipoPlaga,
    descripcion = descripcion
)

fun IncidenciaEntity.toDomain(): Incidencia = Incidencia(
    idIncidencia = idIncidencia,
    idSurco = idSurco,
    idTipoPlaga = idTipoPlaga,
    idUsuario = idUsuario,
    fecha = fecha,
    nivelAlerta = nivelAlerta,
    comentarios = comentarios,
    fotoUrl = fotoUrl,
    sincronizado = sincronizado
)

fun Incidencia.toEntity(): IncidenciaEntity = IncidenciaEntity(
    idIncidencia = idIncidencia,
    idSurco = idSurco,
    idTipoPlaga = idTipoPlaga,
    idUsuario = idUsuario,
    fecha = fecha,
    nivelAlerta = nivelAlerta,
    comentarios = comentarios,
    fotoUrl = fotoUrl,
    sincronizado = sincronizado
)

fun DetalleIncidenciasEntity.toDomain(): DetalleIncidencia = DetalleIncidencia(
    idDetalleIncidencia = idDetalleIncidencia,
    idIncidencia = idIncidencia,
    idTipoPlaga = idTipoPlaga,
    idPlaga = idPlaga,
    cantidadPlaga = cantidadPlaga
)

fun DetalleIncidencia.toEntity(): DetalleIncidenciasEntity = DetalleIncidenciasEntity(
    idDetalleIncidencia = idDetalleIncidencia,
    idIncidencia = idIncidencia,
    idTipoPlaga = idTipoPlaga,
    idPlaga = idPlaga,
    cantidadPlaga = cantidadPlaga
)
