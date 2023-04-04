package uz.izharul.ifadat.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.izharul.ifadat.data.local.AppDatabase
import uz.izharul.ifadat.data.remote.ApiClient.server
import uz.izharul.ifadat.data.remote.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Server

    @Provides
    @Singleton
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun apiService(): ApiService = getRetrofitClient().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    // Database

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun kirishDao(appDatabase: AppDatabase) = appDatabase.kirishDao()

    @Provides
    @Singleton
    fun bobDao(appDatabase: AppDatabase) = appDatabase.bobDao()

    @Provides
    @Singleton
    fun lessonDao(appDatabase: AppDatabase) = appDatabase.lessonDao()

    @Provides
    @Singleton
    fun tasbihDao(appDatabase: AppDatabase) = appDatabase.tasbihDao()

    @Provides
    @Singleton
    fun duaDao(appDatabase: AppDatabase) = appDatabase.duaDao()

    @Provides
    @Singleton
    fun duaListDao(appDatabase: AppDatabase) = appDatabase.duaListDao()

}