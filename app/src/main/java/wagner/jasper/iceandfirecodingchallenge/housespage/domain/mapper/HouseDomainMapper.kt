package wagner.jasper.iceandfirecodingchallenge.housespage.domain.mapper

import wagner.jasper.iceandfirecodingchallenge.housespage.data.model.HouseDTO
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.HousePagingData

fun HouseDTO.toDomain(page: Int, nextPage: Int?) =
    House(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        seats = seats,
        currentLord = currentLord,
        pagingData = HousePagingData(page, nextPage)
    )
