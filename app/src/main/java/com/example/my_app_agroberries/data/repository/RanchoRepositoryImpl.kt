package com.example.my_app_agroberries.data.repository
import com.example.my_app_agroberries.data.local.dao.RanchoDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Rancho
import com.example.my_app_agroberries.domain.repository.RanchoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RanchoRepositoryImpl @Inject constructor(
    private val ranchoDao: RanchoDao
) : RanchoRepository {

    override fun getRanchosByUsuario(idUsuario: Int): Flow<List<Rancho>> {
        // .map  transforma cada lista de Entity a lista de Model
        return ranchoDao.getRanchosByUsuario(idUsuario)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun guardarRanchos(ranchos: List<Rancho>) {
        ranchoDao.insertAll(ranchos.map { it.toEntity() })
    }
}