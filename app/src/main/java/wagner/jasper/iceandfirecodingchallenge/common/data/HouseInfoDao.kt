package wagner.jasper.iceandfirecodingchallenge.common.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.HOUSE_TABLE_NAME
import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity

@Dao
interface HouseInfoDao {
    @Query("SELECT * FROM $HOUSE_TABLE_NAME")
    fun getPagedHouse(): PagingSource<Int, HouseDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeHouses(houses: List<HouseDbEntity>)

    @Query("SELECT * FROM $HOUSE_TABLE_NAME WHERE id = :id")
    fun getHouseById(id: Int): Flow<HouseDbEntity>

    @Query("DELETE FROM $HOUSE_TABLE_NAME")
    suspend fun clearHouses()
}