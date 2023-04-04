package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.TasbihDao
import uz.izharul.ifadat.model.Tasbih
import javax.inject.Inject

class TasbihDetailsRepository @Inject constructor(
    private val tasbihDao: TasbihDao
) {

    suspend fun saveTasbih(data: Tasbih) = tasbihDao.saveTasbih(data)

}