package wagner.jasper.iceandfirecodingchallenge.housespage.domain.usecase

import wagner.jasper.iceandfirecodingchallenge.housespage.data.repository.HousesRepository
import javax.inject.Inject

class GetHousesUseCase @Inject constructor(
    private val repository: HousesRepository
) {
    operator fun invoke() = repository.getHouses()
}