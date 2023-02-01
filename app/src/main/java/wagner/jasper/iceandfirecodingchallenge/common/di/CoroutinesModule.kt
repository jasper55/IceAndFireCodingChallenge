package wagner.jasper.iceandfirecodingchallenge.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.IO

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @IO
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}