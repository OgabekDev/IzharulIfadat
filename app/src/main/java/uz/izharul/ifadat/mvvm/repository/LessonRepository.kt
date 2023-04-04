package uz.izharul.ifadat.mvvm.repository

import uz.izharul.ifadat.data.local.LessonDao
import uz.izharul.ifadat.data.remote.ApiService
import uz.izharul.ifadat.model.Lesson
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val lessonDao: LessonDao,
    private val apiService: ApiService
) {

    suspend fun getFromDatabase(id: Int) = lessonDao.getLesson(id)

    suspend fun saveToDatabase(data: Lesson) = lessonDao.saveLesson(data)

    suspend fun getFromServer(id: Int) = apiService.getLesson(id)

    suspend fun getVersion(id: Int) = apiService.getLessonVersion(id)

}