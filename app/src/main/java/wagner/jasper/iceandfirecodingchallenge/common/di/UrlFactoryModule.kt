package wagner.jasper.iceandfirecodingchallenge.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wagner.jasper.iceandfirecodingchallenge.common.network.UrlFactory

@Module
@InstallIn(SingletonComponent::class)
object UrlFactoryModule {

    @Provides
    fun provideUrlFactory() = UrlFactory()
}
