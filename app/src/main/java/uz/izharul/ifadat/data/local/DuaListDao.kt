package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.izharul.ifadat.model.Dua
import uz.izharul.ifadat.model.DuaList

@Dao
interface DuaListDao {

    @Query("SELECT * FROM dua_list")
    suspend fun getDuas(): List<DuaList>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDua(data: DuaList)

    @Query("DELETE FROM dua_list")
    suspend fun deleteAllDua()

}