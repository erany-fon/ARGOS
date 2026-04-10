package com.example.my_app_agroberries.domain.repository
import com.example.my_app_agroberries.domain.model.IncidenciaPlaga
import kotlinx.coroutines.flow.Flow

interface IncidenciaRepository {

    // guarda una incidencia nueva en local esto ayuda a que se guardan los datos sin internet
    suspend fun guardarIncidencia(incidencia: IncidenciaPlaga)

    // historial de registros del usuario
    fun getIncidenciasByUsuario(idUsuario: Int): Flow<List<IncidenciaPlaga>>

    // las que aún no llegaron al servidor
    suspend fun getPendientesSincronizar(): List<IncidenciaPlaga>

    // marca una incidencia como ya enviada
    suspend fun marcarSincronizada(idIncidencia: Int)
}