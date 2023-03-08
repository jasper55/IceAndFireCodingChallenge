package wagner.jasper.iceandfirecodingchallenge.charachterInfo

import android.database.sqlite.SQLiteException
import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import wagner.jasper.iceandfirecodingchallenge.charachterInfo.testdata.characterDBEntity
import wagner.jasper.iceandfirecodingchallenge.charachterInfo.testdata.characterInfoDTO
import wagner.jasper.iceandfirecodingchallenge.charachterInfo.testdata.goTCharacter
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.repository.CharacterInfoDao
import wagner.jasper.iceandfirecodingchallenge.character.domain.usecase.GetCharacterUseCase
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory

class GetCharacterInfoUseCaseTest {
    @Test
    fun `whenInvokingGetCharacter AndCharacterIsAlreadyStoredInLocalDB dataClientIsNotCalled`() =
        runBlocking {
            // given
            val characterId = 1
            val validUrl = "url"

            val characterDBEntity = characterDBEntity(characterId)
            val characterInfoDTO = characterInfoDTO()
            val expectedData = goTCharacter(characterId)

            val localDB = mockk<CharacterInfoDao> {
                coEvery { getCharacterById(characterId) } returns characterDBEntity
            }

            val dataClient = mockk<DataClient> {
                coEvery { getCharacter(any()) } returns Either.Right(characterInfoDTO)
            }

            val urlFactory = mockk<UrlFactory> {
                every { getCharacterId(any()) } returns characterId
            }

            // when
            val sut = GetCharacterUseCase(dataClient, localDB, urlFactory)
            val result = sut(validUrl)

            // then
            assertThat(result).isEqualTo(Either.Right(expectedData))
            coVerify(exactly = 1) { localDB.getCharacterById(any()) }
            coVerify(exactly = 0) { localDB.storeCharacter(any()) }
            coVerify(exactly = 0) { dataClient.getCharacter(any()) }
        }

    @Test
    fun `whenInvokingGetCharacter AndCharacterIsn'tAlreadyStoredInLocalDB dataClientIsCalled`() =
        runBlocking {
            // given
            val characterId = 1
            val validUrl = "url"

            val characterDBEntity = null
            val characterInfoDTO = characterInfoDTO()
            val expectedData = goTCharacter(characterId)

            val localDB = mockk<CharacterInfoDao> {
                coEvery { getCharacterById(characterId) } returns characterDBEntity
                coEvery { storeCharacter(any()) } returns Unit
            }

            val dataClient = mockk<DataClient> {
                coEvery { getCharacter(any()) } returns Either.Right(characterInfoDTO)
            }

            val urlFactory = mockk<UrlFactory> {
                every { getCharacterId(any()) } returns characterId
            }

            // when
            val sut = GetCharacterUseCase(dataClient, localDB, urlFactory)
            val result = sut(validUrl)

            // then
            assertThat(result).isEqualTo(Either.Right(expectedData))
            coVerify(exactly = 1) { localDB.getCharacterById(any()) }
            coVerify(exactly = 1) { dataClient.getCharacter(any()) }
            coVerify(exactly = 1) { localDB.storeCharacter(any()) }
        }

    @Test
    fun `whenInvokingGetCharacter AndLocalDbThrowsError dataClientIsCalled`() =
        runBlocking {
            // given
            val characterId = 1
            val validUrl = "url"

            val characterDBEntity = characterDBEntity(characterId)
            val characterInfoDTO = characterInfoDTO()
            val expectedData = goTCharacter(characterId)

            val localDB = mockk<CharacterInfoDao> {
                coEvery { getCharacterById(characterId) } throws SQLiteException()
                coEvery { storeCharacter(characterDBEntity) } returns Unit
            }

            val dataClient = mockk<DataClient> {
                coEvery { getCharacter(any()) } returns Either.Right(characterInfoDTO)
            }

            val urlFactory = mockk<UrlFactory> {
                every { getCharacterId(any()) } returns characterId
            }

            // when
            val sut = GetCharacterUseCase(dataClient, localDB, urlFactory)
            val result = sut(validUrl)

            // then
            assertThat(result).isEqualTo(Either.Right(expectedData))
            coVerify(exactly = 1) { localDB.getCharacterById(any()) }
            coVerify(exactly = 1) { dataClient.getCharacter(any()) }
            coVerify(exactly = 1) { localDB.storeCharacter(any()) }
        }

    @Test
    fun `whenInvokingGetCharacter AndLocalDbAndDataClientThrowsError resultHasErrorAndStoreCharacterIsNotCalled`() =
        runBlocking {
            // given
            val characterId = 1
            val validUrl = "url"
            val errorResponse = Either.Left(Exception())

            val characterDBEntity = characterDBEntity(characterId)

            val localDB = mockk<CharacterInfoDao> {
                coEvery { getCharacterById(characterId) } throws SQLiteException()
                coEvery { storeCharacter(characterDBEntity) } returns Unit
            }

            val dataClient = mockk<DataClient> {
                coEvery { getCharacter(any()) } returns errorResponse
            }

            val urlFactory = mockk<UrlFactory> {
                every { getCharacterId(any()) } returns characterId
            }

            // when
            val sut = GetCharacterUseCase(dataClient, localDB, urlFactory)
            val result = sut(validUrl)

            // then
            assertThat(result).isEqualTo(errorResponse)
            coVerify(exactly = 1) { localDB.getCharacterById(any()) }
            coVerify(exactly = 1) { dataClient.getCharacter(any()) }
            coVerify(exactly = 0) { localDB.storeCharacter(any()) }
        }
}
