package wagner.jasper.iceandfirecodingchallenge.common.data

import kotlinx.coroutines.flow.Flow

interface PagingKeyStorage {
    suspend fun storeNextPage(page: Int?)
    val nextPage: Flow<Int?>
    suspend fun reset()
}