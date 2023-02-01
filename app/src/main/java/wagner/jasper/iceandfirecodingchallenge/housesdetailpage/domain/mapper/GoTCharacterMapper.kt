package wagner.jasper.iceandfirecodingchallenge.housesdetailpage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.housesdetailpage.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.housesdetailpage.domain.model.GoTCharacter

fun CharacterDTO.toDomain() =
    GoTCharacter(
        url = url,
        name = name,
    )