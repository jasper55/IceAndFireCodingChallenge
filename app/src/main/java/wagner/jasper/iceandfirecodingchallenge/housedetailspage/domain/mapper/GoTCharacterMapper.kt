package wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter

fun CharacterDTO.toDomain(id: Int) =
    GoTCharacter(
        id = id,
        url = url,
        name = name,
        gender = gender,
    )
