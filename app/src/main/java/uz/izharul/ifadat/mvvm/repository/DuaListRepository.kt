package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.DuaListDao
import uz.izharul.ifadat.data.remote.ApiService
import uz.izharul.ifadat.model.DuaList
import javax.inject.Inject

class DuaListRepository @Inject constructor(
    private val apiService: ApiService,
    private val duaListDao: DuaListDao
) {

    suspend fun getFromServer() = apiService.getDuaList()

    suspend fun saveToDatabase(data: DuaList) = duaListDao.saveDua(data)

    suspend fun getFromDatabase() = duaListDao.getDuas()

    suspend fun deleteAll() = duaListDao.deleteAllDua()

}