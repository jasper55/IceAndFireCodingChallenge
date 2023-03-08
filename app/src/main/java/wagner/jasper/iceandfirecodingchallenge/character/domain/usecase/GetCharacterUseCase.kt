package wagner.jasper.iceandfirecodingchallenge.character.domain.usecase

import android.database.sqlite.SQLiteException
import arrow.core.Either
import arrow.core.right
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.repository.CharacterInfoDao
import wagner.jasper.iceandfirecodingchallenge.character.domain.mapper.toDbEntity
import wagner.jasper.iceandfirecodingchallenge.character.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val dataClient: DataClient,
    private val localDb: CharacterInfoDao,
    private val urlFactory: UrlFactory,
) {
    suspend operator fun invoke(url: String): Either<Exception, GoTCharacter> {
        val id = urlFactory.getCharacterId(url)
        val character = try {
            localDb.getCharacterById(id)
        } catch (e: SQLiteException) {
            null
        }
        return character?.toDomain()?.right()
            ?: dataClient.getCharacter(url).map {
                localDb.storeCharacter(it.toDbEntity(id))
                it.toDomain(id)
            }
    }
}
