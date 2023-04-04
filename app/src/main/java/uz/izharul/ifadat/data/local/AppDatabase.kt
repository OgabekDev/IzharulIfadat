package uz.izharul.ifadat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.converter.gson.GsonConverterFactory
import uz.izharul.ifadat.model.*

@Database(entities = [KirishIzoh::class, BobFromDatabase::class, Lesson::class, Tasbih::class, DuaList::class, Dua::class], version = 6, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun kirishDao(): KirishDao
    abstract fun bobDao(): BobDao
    abstract fun lessonDao(): LessonDao
    abstract fun tasbihDao(): TasbihDao
    abstract fun duaDao(): DuaDao
    abstract fun duaListDao(): DuaListDao

    companion object {
        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database.db"
                )
//                    .addTypeConverter(GsonConverterFactory.create())
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }

}