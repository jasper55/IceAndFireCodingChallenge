package wagner.jasper.iceandfirecodingchallenge.common.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATA_STORE_NAME = "paging_key.store"
private val NEXT_PAGE_KEY = intPreferencesKey("next_page")
private const val PAGE_NOT_AVAILABLE_FALLBACK = -1

@Singleton
class PagingKeyDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : PagingKeyStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    override val nextPage: Flow<Int?> = context.dataStore.data.map {
        val page = it[NEXT_PAGE_KEY]
        if (page == PAGE_NOT_AVAILABLE_FALLBACK) null else page
    }

    override suspend fun storeNextPage(page: Int?) {
        context.dataStore.edit { settings ->
            settings[NEXT_PAGE_KEY] = page ?: PAGE_NOT_AVAILABLE_FALLBACK
        }
    }

    override suspend fun reset() {
        storeNextPage(1)
    }
}