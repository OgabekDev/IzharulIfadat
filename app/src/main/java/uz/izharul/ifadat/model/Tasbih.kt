package uz.izharul.ifadat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasbih")
data class Tasbih(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var color: Long = 0xFFD69B57,
    var countLimit: Int = 33,
    var count: Int = 0
): Serializable

