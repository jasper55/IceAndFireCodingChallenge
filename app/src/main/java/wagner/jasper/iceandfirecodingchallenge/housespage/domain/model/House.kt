package wagner.jasper.iceandfirecodingchallenge.housespage.domain.model

import kotlin.Int.Companion.MAX_VALUE
import kotlin.random.Random

data class House(
    val id: Int = Random(MAX_VALUE).nextInt(),
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val currentLord: String,
    val seats: List<String>,
    val pagingData: HousePagingData,
)

data class HousePagingData(
    val page: Int,
    val nextPage: Int?,
)
