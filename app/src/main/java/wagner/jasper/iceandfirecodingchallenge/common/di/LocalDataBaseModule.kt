package wagner.jasper.iceandfirecodingchallenge.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wagner.jasper.iceandfirecodingchallenge.common.data.CharacterInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.data.DataBaseConstants.GOT_DATA_BASE_NAME
import wagner.jasper.iceandfirecodingchallenge.common.data.HouseInfoDao
import wagner.jasper.iceandfirecodingchallenge.common.data.LocalRoomDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModule {

    @Provides
    @Singleton
    fun provideLocalDB(@ApplicationContext context: Context): LocalRoomDataBase =
        Room.databaseBuilder(context, LocalRoomDataBase::class.java, GOT_DATA_BASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideHouseDao(db: LocalRoomDataBase): HouseInfoDao =
        db.getHouseDao()

    @Provides
    @Singleton
    fun provideCharacterDao(db: LocalRoomDataBase): CharacterInfoDao =
        db.getCharacterDao()
}