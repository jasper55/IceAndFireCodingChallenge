package wagner.jasper.iceandfirecodingchallenge.charachterInfo

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
import wagner.jasper.iceandfirecodingchallenge.character.domain.usecase.GetCharacterUseCase
import wagner.jasper.iceandfirecodingchallenge.character.presentation.viewmodel.CharacterViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

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
    fun `whenLoadingOneCharacter AndLoadingSucceeds resultsAreMatchingExpectation`() =
        runTest {
            // given
            val characterUrl = "url"
            val expectedCharacter = goTCharacter(url = characterUrl)

            val getCharacterUseCase = mockk<GetCharacterUseCase> {
                coEvery { this@mockk.invoke(characterUrl) } returns Either.Right(expectedCharacter)
            }

            // when
            val sut = CharacterViewModel(getCharacterUseCase)
            val job = sut.loadCharacter(characterUrl)
            job.join()

            // then
            assertThat(sut.characters.first()).isEqualTo(mapOf(characterUrl to expectedCharacter))
            assertThat(sut.hasError.first()).isEqualTo(mapOf(characterUrl to false))
            assertThat(sut.isLoading.first()).isEqualTo(mapOf(characterUrl to false))
            job.cancel()
        }

    @Test
    fun `whenLoadingSeveralCharacters AndLoadingSucceeds resultsAreMatchingExpectation`() =
        runTest {
            // given
            val characterUrl = "url"
            val characterUrl2 = "url2"
            val errorUrl = "error"
            val expectedCharacter = goTCharacter(url = characterUrl)
            val expectedCharacter2 = goTCharacter(url = characterUrl2)

            val getCharacterUseCase = mockk<GetCharacterUseCase> {
                coEvery { this@mockk.invoke(characterUrl) } returns Either.Right(expectedCharacter)
                coEvery { this@mockk.invoke(characterUrl2) } returns Either.Right(expectedCharacter2)
                coEvery { this@mockk.invoke(errorUrl) } returns Either.Left(Exception())
            }

            // when
            val sut = CharacterViewModel(getCharacterUseCase)
            val job = sut.loadCharacter(characterUrl)
            val job1 = sut.loadCharacter("")
            val job2 = sut.loadCharacter(characterUrl2)
            val job3 = sut.loadCharacter(errorUrl)
            job.join()
            job1.join()
            job2.join()
            job3.join()

            // then
            assertThat(sut.characters.first()).isEqualTo(
                mapOf(
                    characterUrl to expectedCharacter,
                    characterUrl2 to expectedCharacter2,
                ),
            )
            assertThat(sut.hasError.first()).isEqualTo(
                mapOf(
                    characterUrl to false,
                    characterUrl2 to false,
                    errorUrl to true,
                ),
            )
            assertThat(sut.isLoading.first()).isEqualTo(
                mapOf(
                    characterUrl to false,
                    characterUrl2 to false,
                    errorUrl to false,
                ),
            )
            job.cancel()
            job1.cancel()
            job2.cancel()
            job3.cancel()
        }

    @Test
    fun `whenLoadingCharacterWithSameUrl AndLoadingSucceeds resultsAreMatchingExpectation`() =
        runTest {
            // given
            val characterUrl = "url"
            val characterUrl2 = characterUrl
            val expectedCharacter = goTCharacter(url = characterUrl)

            val getCharacterUseCase = mockk<GetCharacterUseCase> {
                coEvery { this@mockk.invoke(characterUrl) } returns Either.Right(expectedCharacter)
            }

            // when
            val sut = CharacterViewModel(getCharacterUseCase)
            val job = sut.loadCharacter(characterUrl)
            val job2 = sut.loadCharacter(characterUrl2)
            job.join()
            job2.join()

            // then
            assertThat(sut.characters.first()).isEqualTo(
                mapOf(
                    characterUrl to expectedCharacter,
                ),
            )
            assertThat(sut.hasError.first()).isEqualTo(
                mapOf(
                    characterUrl to false,
                ),
            )
            assertThat(sut.isLoading.first()).isEqualTo(
                mapOf(
                    characterUrl to false,
                ),
            )
            job.cancel()
            job2.cancel()
        }
}
