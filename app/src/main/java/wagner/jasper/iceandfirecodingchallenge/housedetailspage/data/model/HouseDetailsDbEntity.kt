package wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.HOUSE_DETAILS_TABLE_NAME

@Entity(tableName = HOUSE_DETAILS_TABLE_NAME)
data class HouseDetailsDbEntity(
    @PrimaryKey val id: Int = 0,
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String>,
    val seats: List<String>,
    val currentLord: String,
    val heir: String,
    val overlord: String,
    val founded: String,
    val founder: String,
    val diedOut: String,
    val ancestralWeapons: List<String>,
    val cadetBranches: List<String>,
    val swornMembers: List<String>,
)
