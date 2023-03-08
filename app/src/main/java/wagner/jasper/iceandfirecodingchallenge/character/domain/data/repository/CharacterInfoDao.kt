package wagner.jasper.iceandfirecodingchallenge.character.domain.data.repository

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.CHARACTER_TABLE_NAME

@Dao
interface CharacterInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun storeCharacter(character: CharacterDbEntity)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE id = :id")
    @Throws(SQLiteException::class)
    suspend fun getCharacterById(id: Int): CharacterDbEntity?
}
