package uz.izharul.ifadat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.izharul.ifadat.model.BobFromDatabase

@Dao
interface BobDao {

    @Query("SELECT * FROM bob WHERE id= :id")
    suspend fun getBob(id: Int): BobFromDatabase?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBob(data: BobFromDatabase)

}