package wagner.jasper.iceandfirecodingchallenge.housespage

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import wagner.jasper.iceandfirecodingchallenge.common.data.HouseInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.data.PagingKeyStorage
import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.repository.PAGE_SIZE
import wagner.jasper.iceandfirecodingchallenge.housespage.paging.HouseRemoteMediator
import wagner.jasper.iceandfirecodingchallenge.housespage.testdata.houseDTO

@OptIn(ExperimentalPagingApi::class)
class HouseRemoteMediatorTest {
    @Test
    fun `whenLoadFunctionOfMediatorIsCalledForFirstPage andReturnsTwoHouses HousesWillBeStoredAndHaveCorrectValues`() =
        runBlocking {
            val requestedPage = 1

            val dtos = listOf(houseDTO(("url1")), houseDTO("url2"))

            val pagingStorage = mockk<PagingKeyStorage> {
                every { nextPage } returns flowOf(requestedPage)
                coEvery { reset() } returns Unit
                coEvery { storeNextPage(any()) } returns Unit
            }

            val client = mockk<DataClient> {
                coEvery {
                    getHouses(
                        eq(requestedPage),
                        eq(PAGE_SIZE),
                    )
                } returns Either.Right(mapOf(requestedPage to dtos))
            }

            val storedHouses = mutableListOf<HouseDbEntity>()
            val localDb = mockk<HouseInfoDao> {
                coEvery { storeHouses(any()) } answers {
                    val entities = firstArg<List<HouseDbEntity>>()
                    storedHouses.addAll(entities)
                }
                coEvery { clearHouses() } returns Unit
            }

            val urlFactory = mockk<UrlFactory> {
                every { getHouseId(any()) } returns 10
            }

            val sut = HouseRemoteMediator(client, localDb, pagingStorage, urlFactory)

            val result = sut.load(
                loadType = LoadType.REFRESH,
                state = PagingState(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(PAGE_SIZE),
                    leadingPlaceholderCount = PAGE_SIZE,
                ),
            )

            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val success = result as RemoteMediator.MediatorResult.Success
            assertThat(success.endOfPaginationReached).isFalse()

            assertThat(storedHouses).hasSize(dtos.size)
            assertThat(storedHouses[0].name).isEqualTo(dtos[0].name)
            assertThat(storedHouses[1].name).isEqualTo(dtos[1].name)
        }

    @Test
    fun `whenLoadFunctionOfMediatorIsCalledForSecondPage andReturnsTwoHouses HousesWillBeStoredAndHaveCorrectValues`(): Unit =
        runBlocking {
            val requestedPage: Int? = null

            val dtos = listOf(houseDTO(("url1")), houseDTO("url2"))

            val pagingStorage = mockk<PagingKeyStorage> {
                every { nextPage } returns flowOf(requestedPage)
                coEvery { reset() } returns Unit
                coEvery { storeNextPage(any()) } returns Unit
            }

            val client = mockk<DataClient> {
                coEvery {
                    getHouses(any(), any())
                } returns Either.Right(mapOf(requestedPage to dtos))
            }

            val localDb = mockk<HouseInfoDao> {
                coEvery { storeHouses(any()) } returns Unit
                coEvery { clearHouses() } returns Unit
            }

            val urlFactory = mockk<UrlFactory> {
                every { getHouseId(any()) } returns 10
            }

            val sut = HouseRemoteMediator(client, localDb, pagingStorage, urlFactory)

            val result = sut.load(
                loadType = LoadType.APPEND,
                state = PagingState(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(PAGE_SIZE),
                    leadingPlaceholderCount = PAGE_SIZE,
                ),
            )

            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val success = result as RemoteMediator.MediatorResult.Success
            assertThat(success.endOfPaginationReached).isTrue()
        }

    @Test
    fun `whenLoadFunctionOfMediatorIsCalledForSecondPage andReturnsErrorWhenRetirevingPageData theReturnError`(): Unit =
        runBlocking {
            val requestedPage = 1

            val exception = IllegalStateException("Did not work")
            val pagingStorage = mockk<PagingKeyStorage> {
                every { nextPage } returns flowOf(requestedPage)
                coEvery { reset() } returns Unit
                coEvery { storeNextPage(any()) } returns Unit
            }

            val client = mockk<DataClient> {
                coEvery {
                    getHouses(any(), any())
                } returns Either.Left(exception)
            }

            val localDb = mockk<HouseInfoDao> {
                coEvery { storeHouses(any()) } returns Unit
                coEvery { clearHouses() } returns Unit
            }

            val urlFactory = mockk<UrlFactory> {
                every { getHouseId(any()) } returns 10
            }

            val sut = HouseRemoteMediator(client, localDb, pagingStorage, urlFactory)

            val result = sut.load(
                loadType = LoadType.APPEND,
                state = PagingState(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(PAGE_SIZE),
                    leadingPlaceholderCount = PAGE_SIZE,
                ),
            )
            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
            val error = result as RemoteMediator.MediatorResult.Error
            assertThat(error.throwable).isEqualTo(exception)
        }
}
