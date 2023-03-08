package wagner.jasper.iceandfirecodingchallenge.charachterInfo.testdata

import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter

fun characterInfoDTO(
    url: String = "url",
    name: String = "name",
    gender: String = "female",
) = CharacterDTO(url, name, gender)

fun characterDBEntity(
    id: Int = 1,
    url: String = "url",
    name: String = "name",
    gender: String = "female",
) = CharacterDbEntity(id, url, name, gender)

fun goTCharacter(
    id: Int = 1,
    url: String = "url",
    name: String = "name",
    gender: String = "female",
) = GoTCharacter(id, url, name, gender)
