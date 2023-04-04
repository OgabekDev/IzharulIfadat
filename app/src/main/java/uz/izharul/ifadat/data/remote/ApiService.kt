package uz.izharul.ifadat.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.izharul.ifadat.model.*

interface ApiService {

    // Kirish & Izoh
    @GET("main")
    suspend fun getKirish(): Response<KirishIzoh>

    @GET("main/version")
    suspend fun getKirishVersion(): Response<Version>

    // Bob
    @GET("chapters/{id}")
    suspend fun getBob(@Path("id") id: Int): Response<Bob>

    @GET("chapters/{id}/version")
    suspend fun getChapterVersion(@Path("id") id: Int): Response<Version>

    // Lesson
    @GET("lessons/{id}")
    suspend fun getLesson(@Path("id") id: Int): Response<Lesson>

    @GET("lessons/{id}/version")
    suspend fun getLessonVersion(@Path("id") id: Int): Response<Version>

    // Dua
    @GET("dua")
    suspend fun getDuaList(): Response<List<DuaList>>

    @GET("dua/{id}")
    suspend fun getDua(@Path("id") id: Int): Response<Dua>

}