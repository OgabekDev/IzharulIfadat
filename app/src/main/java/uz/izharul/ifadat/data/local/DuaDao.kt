package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.izharul.ifadat.model.Dua

@Dao
interface DuaDao {

    @Query("SELECT * FROM dua WHERE id = :id")
    suspend fun getDua(id: Int): Dua?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDua(data: Dua)

}