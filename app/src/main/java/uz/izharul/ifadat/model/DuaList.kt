package uz.izharul.ifadat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua_list")
data class DuaList(
    @PrimaryKey
    val id: Int,
    val titleUz: String,
    val titleOz: String
)
