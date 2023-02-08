package wagner.jasper.iceandfirecodingchallenge.housespage.paging

import android.util.Log
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
import wagner.jasper.iceandfirecodingchallenge.housespage.data.mapper.toDbEntity

@OptIn(ExperimentalPagingApi::class)
class HouseRemoteMediator(
    private val dataClient: DataClient,
    private val localDb: LocalRoomDataBase,
    private val pagingKeyStorage: PagingKeyStorage,
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

            val houseEntities = pagedHouses.value.mapIndexed { index, houseDTO ->
                val entity = houseDTO.toDbEntity()
                Log.d("INDEX", "$index ${entity.id}")
                return@mapIndexed entity
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
