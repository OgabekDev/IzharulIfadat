package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.BobDao
import uz.izharul.ifadat.data.remote.ApiService
import uz.izharul.ifadat.model.BobFromDatabase
import javax.inject.Inject

class ChapterRepository @Inject constructor(
    private val apiService: ApiService,
    private val bobDao: BobDao
) {

    suspend fun getFromServer(id: Int) = apiService.getBob(id)
    suspend fun getVersion(id: Int) = apiService.getChapterVersion(id)

    suspend fun saveToDatabase(data: BobFromDatabase) = bobDao.saveBob(data)
    suspend fun getFromDatabase(id: Int) = bobDao.getBob(id)

}