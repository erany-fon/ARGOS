package com.example.my_app_agroberries.data.repository
import com.example.my_app_agroberries.data.local.dao.CultivoDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Cultivo
import com.example.my_app_agroberries.domain.repository.CultivoRepository
import javax.inject.Inject

class CultivoRepositoryImpl @Inject constructor(
    private val cultivoDao: CultivoDao
) : CultivoRepository {
    override suspend fun insertCultivo(cultivo: Cultivo) {
        cultivoDao.insertCultivo(cultivo.toEntity())
    }

    override suspend fun insertAll(cultivos: List<Cultivo>) {
        cultivoDao.insertAll(cultivos.map { it.toEntity() })
    }

    override suspend fun getCultivoById(idCultivo: Int): Cultivo? {
        return cultivoDao.getCultivoById(idCultivo)?.toDomain()
    }

    override suspend fun getAllCultivos(): List<Cultivo> {
        return cultivoDao.getAllCultivos().map { it.toDomain() }
    }

    override suspend fun clearCultivos() {
        cultivoDao.clearCultivos()
    }
}

