package uz.izharul.ifadat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua")
data class Dua(
    @PrimaryKey
    var id: Int,
    var version: Int,
    var titleUz: String,
    var titleOz: String,
    var textUz: String,
    var textOz: String,
    var dicsUz: String,
    var dicsOz: String,
    var audioLink: String,
    var audioTime: Int
)
