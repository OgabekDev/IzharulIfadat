package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.TasbihDao
import uz.izharul.ifadat.model.Tasbih
import javax.inject.Inject

class TasbihRepository @Inject constructor(
    private val tasbihDao: TasbihDao
) {

    suspend fun getList() = tasbihDao.getTasbihList()

    suspend fun delete(data: Tasbih) = tasbihDao.deleteTasbih(data.id)

    suspend fun create(data: Tasbih) = tasbihDao.saveTasbih(data)

}