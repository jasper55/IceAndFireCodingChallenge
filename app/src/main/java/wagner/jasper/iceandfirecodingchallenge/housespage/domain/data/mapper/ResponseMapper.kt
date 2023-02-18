package wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.mapper

import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.model.HouseDTO

fun HouseDTO.toDbEntity() =
    HouseDbEntity(
        id = url.substringAfterLast("/").toInt(),
        url = url,
        name = name,
        region = region,
        currentLord = currentLord,
    )
