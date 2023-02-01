package wagner.jasper.iceandfirecodingchallenge.common.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.CHARACTER_TABLE_NAME
import wagner.jasper.iceandfirecodingchallenge.common.data.model.CharacterDataModel

@Dao
interface CharacterInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeCharacter(character: CharacterDataModel)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE id = :id")
    fun getCharacterById(id: Long): Flow<CharacterDataModel>

    @Query("SELECT id not null FROM $CHARACTER_TABLE_NAME WHERE url = :url")
    fun doesCharacterExist(url: String): Boolean
}