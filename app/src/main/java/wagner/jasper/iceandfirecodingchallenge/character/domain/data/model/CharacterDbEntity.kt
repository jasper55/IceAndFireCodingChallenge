package wagner.jasper.iceandfirecodingchallenge.character.domain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants

@Entity(tableName = DataBaseConstants.CHARACTER_TABLE_NAME)
data class CharacterDbEntity(
    @PrimaryKey val id: Int = 0,
    val url: String,
    val name: String,
    val gender: String,
)
