package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.KirishDao
import uz.izharul.ifadat.data.remote.ApiService
import uz.izharul.ifadat.model.KirishIzoh
import javax.inject.Inject

class KirishRepository @Inject constructor(
    private val apiService: ApiService,
    private val kirishDao: KirishDao
) {

    suspend fun getVersion() = apiService.getKirishVersion()

    suspend fun getFromServer() = apiService.getKirish()

    suspend fun saveToDatabase(data: KirishIzoh) = kirishDao.saveKirish(data)

    suspend fun getFromDatabase() = kirishDao.getKirish()

}