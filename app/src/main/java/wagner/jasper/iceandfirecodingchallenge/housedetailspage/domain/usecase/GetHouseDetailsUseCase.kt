package wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase

import arrow.core.Either
import arrow.core.right
import wagner.jasper.iceandfirecodingchallenge.common.data.HouseInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper.toDbEntity
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import javax.inject.Inject

class GetHouseDetailsUseCase @Inject constructor(
    private val client: DataClient,
    private val houseInfoDB: HouseInfoDao,
) {
    suspend operator fun invoke(id: Int): Either<Exception, HouseDetails> {
        val houseDetails = houseInfoDB.getHouseDetailsById(id)
        return houseDetails?.toDomain()?.right()
            ?: client.getHouseDetails(id).map {
                houseInfoDB.storeHouse(it.toDbEntity())
                it.toDomain()
            }
    }
}
