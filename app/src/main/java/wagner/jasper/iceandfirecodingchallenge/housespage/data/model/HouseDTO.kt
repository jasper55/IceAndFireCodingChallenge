package wagner.jasper.iceandfirecodingchallenge.housespage.data.model

import kotlinx.serialization.Serializable

@Serializable
class HouseDTO(
    val url: String,
    val name: String,
    val region: String,
    val currentLord: String,
)
