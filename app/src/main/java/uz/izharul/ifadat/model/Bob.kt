package uz.izharul.ifadat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Bob(
    val id: Int,
    val version: Int,
    val titleUz: String,
    val titleOz: String,
    val lessons: List<BobInfo>
)

data class BobInfo(
    val id: Int,
    val version: Int
): Serializable

@Entity(tableName = "bob")
data class BobFromDatabase(
    @PrimaryKey
    var id: Int,
    var titleUz: String,
    var titleOz: String,
    var lessons: String,
    val version: Int
)