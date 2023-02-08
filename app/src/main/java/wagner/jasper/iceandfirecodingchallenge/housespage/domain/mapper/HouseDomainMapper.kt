package wagner.jasper.iceandfirecodingchallenge.housespage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House

fun HouseDbEntity.toDomain() =
    House(
        id = id,
        url = url,
        name = name,
        region = region,
        currentLord = currentLord,
    )
