package wagner.jasper.iceandfirecodingchallenge.housespage.data.repository

import androidx.paging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import wagner.jasper.iceandfirecodingchallenge.common.data.LocalRoomDataBase
import wagner.jasper.iceandfirecodingchallenge.common.data.PagingKeyStorage
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.IO
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.paging.HouseRemoteMediator
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

const val PAGE_SIZE = 20

@Singleton
class HousesRepository @Inject constructor(
    private val dataClient: DataClient,
    private val localDb: LocalRoomDataBase,
    private val pagingKeyStorage: PagingKeyStorage,
    @IO private val coroutineDispatcher: CoroutineDispatcher,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getHouses(): Flow<PagingData<House>> {
        val pagingSourceFactory = { localDb.getHouseDao().getPagedHouse() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE),
            remoteMediator = HouseRemoteMediator(dataClient, localDb, pagingKeyStorage),
            pagingSourceFactory = pagingSourceFactory
        )
            .flow
            .map { houseEntity ->
                houseEntity.map { it.toDomain() }
            }
            .flowOn(Dispatchers.IO)
    }

//    override val coroutineContext: CoroutineContext = coroutineDispatcher + SupervisorJob()
}