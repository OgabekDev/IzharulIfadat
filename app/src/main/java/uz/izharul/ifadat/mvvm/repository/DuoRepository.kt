package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.DuaDao
import uz.izharul.ifadat.data.remote.ApiService
import uz.izharul.ifadat.model.Dua
import javax.inject.Inject

class DuoRepository @Inject constructor(
    private val apiService: ApiService,
    private val duaDao: DuaDao
) {

    suspend fun getFromServer(id: Int) = apiService.getDua(id)

    suspend fun saveToDatabase(data: Dua) = duaDao.saveDua(data)

    suspend fun getFromDatabase(id: Int) = duaDao.getDua(id)

}