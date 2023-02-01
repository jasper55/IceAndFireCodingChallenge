package wagner.jasper.iceandfirecodingchallenge.housespage.domain.usecase

import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import javax.inject.Inject

class GetHousesUseCase @Inject constructor(
    private val client: DataClient
) {
    suspend operator fun invoke(page: Int) = client.getHouses(page, 20)
}