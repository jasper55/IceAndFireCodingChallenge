package wagner.jasper.iceandfirecodingchallenge.housespage.data.mapper

import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.housespage.data.model.HouseDTO

fun HouseDTO.toDbEntities() =
    HouseDbEntity(
        id = url.substringAfterLast("/").toInt(),
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        currentLord = currentLord,
        words = words,
        seats = seats,
    )
