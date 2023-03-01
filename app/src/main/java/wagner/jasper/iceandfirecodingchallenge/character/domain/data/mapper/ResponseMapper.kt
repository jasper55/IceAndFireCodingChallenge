package wagner.jasper.iceandfirecodingchallenge.character.domain.data.mapper

import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity

fun CharacterDTO.toDbEntity() =
    CharacterDbEntity(
        id = url.substringAfterLast("/").toInt(),
        url = url,
        name = name,
        gender = gender,
    )
