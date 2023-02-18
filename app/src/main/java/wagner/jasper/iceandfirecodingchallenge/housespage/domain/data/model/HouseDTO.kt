package wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
class HouseDTO(
    val url: String,
    val name: String,
    val region: String,
    val currentLord: String,
)
