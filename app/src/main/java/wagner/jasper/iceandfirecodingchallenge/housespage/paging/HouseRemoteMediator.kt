package wagner.jasper.iceandfirecodingchallenge.housespage.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import arrow.core.getOrHandle
import kotlinx.coroutines.flow.first
import wagner.jasper.iceandfirecodingchallenge.common.data.LocalRoomDataBase
import wagner.jasper.iceandfirecodingchallenge.common.data.PagingKeyStorage
import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.common.network.DataClient
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.data.mapper.toDbEntity

@OptIn(ExperimentalPagingApi::class)
class HouseRemoteMediator(
    private val dataClient: DataClient,
    private val localDb: LocalRoomDataBase,
    private val pagingKeyStorage: PagingKeyStorage,
    private val urlFactory: UrlFactory,
) : RemoteMediator<Int, HouseDbEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HouseDbEntity>,
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {
                pagingKeyStorage.reset()
                localDb.getHouseDao().clearHouses()
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {}
        }

        val requestedPage = pagingKeyStorage.nextPage.first()
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val pagedHouses =
                dataClient.getHouses(requestedPage, state.config.pageSize).getOrHandle {
                    return MediatorResult.Error(it)
                }.entries.first()

            val houseEntities = pagedHouses.value.map { houseDTO ->
                val id = urlFactory.getHouseId(houseDTO.url)
                houseDTO.toDbEntity(id)
            }
            localDb.getHouseDao().storeHouses(houseEntities)
            val nextPage = pagedHouses.key
            pagingKeyStorage.storeNextPage(nextPage)
            MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
