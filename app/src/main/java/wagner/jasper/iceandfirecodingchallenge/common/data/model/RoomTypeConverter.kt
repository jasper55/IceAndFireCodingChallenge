package wagner.jasper.iceandfirecodingchallenge.common.data.model

import androidx.room.TypeConverter

object RoomTypeConverter {
    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return value.split(",")
    }
}