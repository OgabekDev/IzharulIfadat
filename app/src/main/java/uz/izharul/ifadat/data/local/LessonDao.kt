package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.izharul.ifadat.model.Lesson

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessons WHERE id = :id")
    suspend fun getLesson(id: Int): Lesson?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLesson(data: Lesson)

}