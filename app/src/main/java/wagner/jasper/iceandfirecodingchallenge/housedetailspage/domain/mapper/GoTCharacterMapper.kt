package wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.GoTCharacter

fun CharacterDTO.toDomain() =
    GoTCharacter(
        url = url,
        name = name,
    )
