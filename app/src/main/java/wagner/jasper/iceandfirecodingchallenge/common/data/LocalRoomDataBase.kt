package wagner.jasper.iceandfirecodingchallenge.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.model.CharacterDbEntity
import wagner.jasper.iceandfirecodingchallenge.character.domain.data.repository.CharacterInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.data.model.HouseDbEntity
import wagner.jasper.iceandfirecodingchallenge.common.data.model.RoomTypeConverter
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.data.model.HouseDetailsDbEntity

@Database(
    entities = [
        HouseDbEntity::class,
        CharacterDbEntity::class,
        HouseDetailsDbEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    RoomTypeConverter::class,
)
abstract class LocalRoomDataBase : RoomDatabase() {
    abstract fun getHouseDao(): HouseInfoDao
    abstract fun getCharacterDao(): CharacterInfoDao
}
