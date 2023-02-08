package wagner.jasper.iceandfirecodingchallenge.common.network

import arrow.core.Either
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDTO
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.housespage.data.model.HouseDTO

interface DataClient {
    suspend fun getHouses(page: Int, pageSize: Int): Either<Exception, Map<Int?, List<HouseDTO>>>
    suspend fun getCharacter(url: String): Either<Exception, GoTCharacter>
    suspend fun getHouseDetails(id: Int): Either<Exception, HouseDetailsDTO>
}
