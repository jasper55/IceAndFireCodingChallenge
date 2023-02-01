package wagner.jasper.iceandfirecodingchallenge.housespage.data.model
import kotlinx.serialization.Serializable

@Serializable
class HouseDTO(
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val currentLord: String,
    val seats: List<String>,
)