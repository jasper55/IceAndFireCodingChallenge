package wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import wagner.jasper.iceandfirecodingchallenge.common.data.HouseInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.data.PagingKeyStorage
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.mapper.toDomain
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.paging.HouseRemoteMediator
import javax.inject.Inject
import javax.inject.Singleton

const val PAGE_SIZE = 20

@Singleton
class HousesRepository @Inject constructor(
    private val dataClient: DataClient,
    private val localHouseDB: HouseInfoDao,
    private val pagingKeyStorage: PagingKeyStorage,
    private val urlFactory: UrlFactory,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getHouses(): Flow<PagingData<House>> {
        val pagingSourceFactory = { localHouseDB.getPagedHouse() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE),
            remoteMediator = HouseRemoteMediator(dataClient, localHouseDB, pagingKeyStorage, urlFactory),
            pagingSourceFactory = pagingSourceFactory,
        )
            .flow
            .map { houseEntity ->
                houseEntity.map { it.toDomain() }
            }
            .flowOn(Dispatchers.IO)
    }
}
