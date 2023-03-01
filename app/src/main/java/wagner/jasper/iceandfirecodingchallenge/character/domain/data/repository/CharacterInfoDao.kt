package wagner.jasper.iceandfirecodingchallenge.character.domain.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.CHARACTER_TABLE_NAME

@Dao
interface CharacterInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeCharacter(character: CharacterDbEntity)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterDbEntity?

    @Query("SELECT id not null FROM $CHARACTER_TABLE_NAME WHERE url = :url")
    suspend fun doesCharacterExist(url: String): Boolean
}
