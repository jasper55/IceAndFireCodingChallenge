package wagner.jasper.iceandfirecodingchallenge.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.HOUSE_TABLE_NAME

@Entity(tableName = HOUSE_TABLE_NAME)
data class HouseDbEntity(
    @PrimaryKey val id: Int = 0,
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val currentLord: String,
    val seats: List<String>
)
