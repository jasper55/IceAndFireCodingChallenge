package wagner.jasper.iceandfirecodingchallenge.housespage.domain.model

data class House(
    val id: Int,
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val currentLord: String,
    val seats: List<String>,
)
