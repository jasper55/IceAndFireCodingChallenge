package wagner.jasper.iceandfirecodingchallenge.common.network

import arrow.core.Either
import wagner.jasper.iceandfirecodingchallenge.housesdetailpage.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House

interface DataClient {
    suspend fun getHouses(page: Int, pageSize: Int): Either<Exception, List<House>>
    suspend fun getCharacter(url: String): Either<Exception, GoTCharacter>
}