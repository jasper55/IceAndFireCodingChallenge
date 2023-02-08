package wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase

import arrow.core.Either
import arrow.core.right
import wagner.jasper.iceandfirecodingchallenge.common.data.LocalRoomDataBase
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper.toDbEntity
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import javax.inject.Inject

class GetHouseDetailsUseCase @Inject constructor(
    private val client: DataClient,
    private val localDb: LocalRoomDataBase,
) {
    suspend operator fun invoke(id: Int): Either<Exception, HouseDetails> {
        val houseDetails = localDb.getHouseDao().getHouseDetailsById(id)
        return houseDetails?.toDomain()?.right()
            ?: client.getHouseDetails(id).map {
                localDb.getHouseDao().storeHouse(it.toDbEntity())
                it.toDomain()
            }
    }
}
