package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.http.DELETE
import uz.izharul.ifadat.model.Tasbih

@Dao
interface TasbihDao {

    @Query("SELECT * FROM tasbih")
    suspend fun getTasbihList(): List<Tasbih>

    @Query("SELECT * FROM tasbih WHERE id = :id")
    suspend fun getTasbih(id: Int): Tasbih

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTasbih(data: Tasbih)

    @Query("DELETE FROM tasbih WHERE id = :id")
    suspend fun deleteTasbih(id: Int)

}