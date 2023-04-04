package uz.izharul.ifadat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "kirish")
data class KirishIzoh(
    var version: Int,
    var mainTextUz: String,
    var mainTextOz: String,
    var mainDicsUz: String,
    var mainDicsOz: String,
    var textUz: String,
    var textOz: String,
    var dicsUz: String,
    var dicsOz: String,
    var audioLink: String,
    var audioTime: Int,
    @PrimaryKey
    var id: Int = 1
): Serializable
