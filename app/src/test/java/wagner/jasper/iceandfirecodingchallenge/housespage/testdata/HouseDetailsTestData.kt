package wagner.jasper.iceandfirecodingchallenge.housespage.testdata

import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.model.HouseDTO
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
fun houseDTO(
    url: String = "url",
    name: String = "name",
    region: String = "region",
    currentLordUrl: String = "currentLordUrl",
) =
    HouseDTO(
        url = url,
        name = name,
        region = region,
        currentLord = currentLordUrl,
    )
fun house(
    id: Int = 1,
    url: String = "url",
    name: String = "name",
    region: String = "region",
    currentLordUrl: String = "currentLordUrl",
) = House(
    id = id,
    url = url,
    name = name,
    region = region,
    currentLord = currentLordUrl,
)
fun houseDBEntity(
    id: Int = 1,
    url: String = "url",
    name: String = "name",
    region: String = "region",
    currentLordUrl: String = "currentLordUrl",
) =
    HouseDbEntity(
        id = id,
        url = url,
        name = name,
        region = region,
        currentLord = currentLordUrl,
    )
