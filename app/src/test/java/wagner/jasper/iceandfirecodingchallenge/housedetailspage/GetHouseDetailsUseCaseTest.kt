package wagner.jasper.iceandfirecodingchallenge.housedetailspage

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import wagner.jasper.iceandfirecodingchallenge.common.data.HouseInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.testdata.houseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.testdata.houseDetailsDBEntity
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.testdata.houseDetailsResponse
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase

class GetHouseDetailsUseCaseTest {
    @Test
    fun `whenInvokingGetHouseDetails AndDetailsAreAlreadyStoredInLocalDB dataClientIsNotCalled`() =
        runBlocking {
            // given
            val houseId = 1

            val houseDetailResponse = houseDetailsDBEntity(houseId)
            val expectedData = houseDetails()

            val localDB = mockk<HouseInfoDao> {
                coEvery { getHouseDetailsById(houseId) } returns houseDetailResponse
            }

            val dataClient = mockk<DataClient> {
                coEvery { getHouseDetails(1) } returns Either.Left(Exception())
            }

            // when
            val sut = GetHouseDetailsUseCase(dataClient, localDB)
            val result = sut(houseId)

            // then
            assertThat(result).isEqualTo(Either.Right(expectedData))
        }

    @Test
    fun `whenInvokingGetHouseDetails AndDetailsAreNotStoredInLocalDB thenDataClientIsCalledAndDataIsStoredToLocalDB`() =
        runBlocking {
            // given
            val houseId = 1

            val houseDetailResponse = houseDetailsResponse()
            val expectedData = houseDetails()

            val localDB = mockk<HouseInfoDao> {
                coEvery { getHouseDetailsById(houseId) } returns null
                coEvery { storeHouse(any()) } returns Unit
            }

            val dataClient = mockk<DataClient> {
                coEvery { getHouseDetails(1) } returns Either.Right(houseDetailResponse)
            }

            // when
            val sut = GetHouseDetailsUseCase(dataClient, localDB)
            val result = sut(houseId)

            // then
            assertThat(result).isEqualTo(Either.Right(expectedData))
            coVerify(exactly = 1) { localDB.storeHouse(any()) }
        }
}
