package wagner.jasper.iceandfirecodingchallenge.character.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
class CharacterDTO(
    val url: String,
    val name: String,
    val gender: String,
)
