package wagner.jasper.iceandfirecodingchallenge.character.domain.usecase

import arrow.core.Either
import arrow.core.right
import wagner.jasper.iceandfirecodingchallenge.character.domain.mapper.toDbEntity
import wagner.jasper.iceandfirecodingchallenge.character.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.common.data.LocalRoomDataBase
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.mapper.toDomain
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val dataClient: DataClient,
    private val localDb: LocalRoomDataBase,
    private val urlFactory: UrlFactory,
) {
    suspend operator fun invoke(url: String): Either<Exception, GoTCharacter> {
        val id = urlFactory.getCharacterId(url)
        val character = localDb.getCharacterDao().getCharacterById(id)
        return character?.toDomain()?.right()
            ?: dataClient.getCharacter(url).map {
                localDb.getCharacterDao().storeCharacter(it.toDbEntity(id))
                it.toDomain(id)
            }
    }
}
