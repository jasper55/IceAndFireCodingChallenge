package wagner.jasper.iceandfirecodingchallenge.housedetailspage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import wagner.jasper.iceandfirecodingchallenge.charachterInfo.testdata.goTCharacter
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.character.domain.usecase.GetCharacterUseCase
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.testdata.houseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.usecase.GetHouseDetailsUseCase
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel.HouseDetailsViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class HouseDetailsViewModelTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `whenLoadingDetailsWithIdIsSuccessful AndUrlsForFounderAndSwornMemberAreNotEmpty resultsAreMatchingExpectation`() =
        runTest {
            val founderUrl = "founderUrl"
            val memberUrl1 = "1"
            val memberUrl2 = "2"
            val expectedHouse =
                houseDetails(founderUrl = founderUrl, swornMembers = listOf(memberUrl1, memberUrl2))
            val expectedFounder = goTCharacter(url = founderUrl)
            val expectedSwornMembers =
                listOf(goTCharacter(url = memberUrl1), goTCharacter(url = memberUrl2))

            val getHouseDetailsUseCase = mockk<GetHouseDetailsUseCase> {
                coEvery { this@mockk.invoke(any()) } returns Either.Right(expectedHouse)
            }

            val getCharacterUseCase = mockk<GetCharacterUseCase> {
                coEvery { this@mockk.invoke(founderUrl) } returns Either.Right(expectedFounder)
                coEvery { this@mockk.invoke(memberUrl1) } returns Either.Right(expectedSwornMembers[0])
                coEvery { this@mockk.invoke(memberUrl2) } returns Either.Right(expectedSwornMembers[1])
            }

            // when
            val sut = HouseDetailsViewModel(getHouseDetailsUseCase, getCharacterUseCase)
            val job = sut.loadHouseDetails(1)
            job.join()

            assertThat(sut.houseDetails.first()).isEqualTo(expectedHouse)
            assertThat(sut.founder.first()).isEqualTo(expectedFounder)
            assertThat(sut.members.first()).isEqualTo(expectedSwornMembers)
            assertThat(sut.hasError.first()).isEqualTo(false)
            assertThat(sut.isLoading.first()).isEqualTo(false)
            job.cancel()
        }

    @Test
    fun `whenLoadingDetailsWithIdThrowsError AndUrlsForFounderAndSwornMemberAreNotEmpty resultsAreMatchingExpectation`() =
        runTest {
            val founderUrl = "founderUrl"
            val memberUrl1 = "1"
            val memberUrl2 = "2"

            val expectedFounder = goTCharacter(url = founderUrl)
            val expectedSwornMembers =
                listOf(goTCharacter(url = memberUrl1), goTCharacter(url = memberUrl2))

            val getHouseDetailsUseCase = mockk<GetHouseDetailsUseCase> {
                coEvery { this@mockk.invoke(any()) } returns Either.Left(Exception())
            }

            val getCharacterUseCase = mockk<GetCharacterUseCase> {
                coEvery { this@mockk.invoke(founderUrl) } returns Either.Right(expectedFounder)
                coEvery { this@mockk.invoke(memberUrl1) } returns Either.Right(expectedSwornMembers[0])
                coEvery { this@mockk.invoke(memberUrl2) } returns Either.Right(expectedSwornMembers[1])
            }

            // when
            val sut = HouseDetailsViewModel(getHouseDetailsUseCase, getCharacterUseCase)
            val job = sut.loadHouseDetails(1)
            job.join()

            assertThat(sut.houseDetails.first()).isEqualTo(null)
            assertThat(sut.founder.first()).isEqualTo(null)
            assertThat(sut.members.first()).isEqualTo(emptyList<GoTCharacter>())
            assertThat(sut.hasError.first()).isEqualTo(true)
            assertThat(sut.isLoading.first()).isEqualTo(false)
            job.cancel()
        }
}
