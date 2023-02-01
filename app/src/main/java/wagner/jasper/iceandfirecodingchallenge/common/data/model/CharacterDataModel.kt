package wagner.jasper.iceandfirecodingchallenge.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.CHARACTER_TABLE_NAME

@Entity(tableName = CHARACTER_TABLE_NAME)
data class CharacterDataModel(
    @PrimaryKey val id: Long,
    val name: String,
    val url: String,
)
