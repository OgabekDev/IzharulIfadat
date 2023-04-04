package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.izharul.ifadat.model.KirishIzoh

@Dao
interface KirishDao {

    @Query("SELECT * FROM kirish WHERE id = :id")
    suspend fun getKirish(id: Int = 0): KirishIzoh?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKirish(data: KirishIzoh)

}