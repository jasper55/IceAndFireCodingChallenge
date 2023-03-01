package wagner.jasper.iceandfirecodingchallenge.common.network

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDTO
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.BaseUrl
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.IO
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDTO
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.model.HouseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoTKtorHttpClient @Inject constructor(
    private val httpClient: HttpClient,
    private val headerInterceptor: HeaderInterceptor,
    @BaseUrl private val baseUrl: String,
    @IO private val coroutineDispatcher: CoroutineDispatcher,
) : DataClient {

    override suspend fun getHouses(
        page: Int,
        pageSize: Int,
    ): Either<Exception, Map<Int?, List<HouseDTO>>> =
        withContext(coroutineDispatcher) {
            delay(5000)
            try {
                val response = httpClient.get("$baseUrl/houses?page=$page&pageSize=$pageSize")
                if (response.status.value == HttpStatusCode.OK.value) {
                    val houses = response.body<List<HouseDTO>>()
                    val nextPage = headerInterceptor.getNextPage(response.headers)
                    mapOf(nextPage to houses).right()
                } else {
                    IllegalStateException("Could not load page $page. Response code: ${response.status}").left()
                }
            } catch (e: Exception) {
                e.left()
            }
        }

    override suspend fun getCharacter(url: String): Either<Exception, CharacterDTO> =
        withContext(coroutineDispatcher) {
            try {
                val response = httpClient.get(url)
                if (response.status.value == HttpStatusCode.OK.value) {
                    response.body<CharacterDTO>().right()
                } else {
                    IllegalStateException("Failed to load character with error code ${response.status.value}").left()
                }
            } catch (e: Exception) {
                e.left()
            }
        }

    override suspend fun getHouseDetails(id: Int): Either<Exception, HouseDetailsDTO> =
        withContext(coroutineDispatcher) {
            try {
                val url = "$baseUrl/houses/$id"
                val response = httpClient.get(url)
                if (response.status.value == HttpStatusCode.OK.value) {
                    response.body<HouseDetailsDTO>().right()
                } else {
                    IllegalStateException("Failed to load character with error code ${response.status.value}").left()
                }
            } catch (e: Exception) {
                e.left()
            }
        }
}
