package wagner.jasper.iceandfirecodingchallenge.character.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter

fun CharacterDbEntity.toDomain() =
    GoTCharacter(
        id = id,
        url = url,
        name = name,
        gender = gender,
    )

fun CharacterDTO.toDbEntity(id: Int) = CharacterDbEntity(
    id = id,
    url = url,
    name = name,
    gender = gender,
)

fun CharacterDTO.toDomain(id: Int) =
    GoTCharacter(
        id = id,
        url = url,
        name = name,
        gender = gender,
    )
