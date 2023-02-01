package wagner.jasper.iceandfirecodingchallenge.common.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import wagner.jasper.iceandfirecodingchallenge.common.di.annotation.BaseUrl
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.usecase.GetHousesUseCase

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun httpClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @Provides
    @BaseUrl
    fun baseUrl(): String = "https://anapioficeandfire.com/api"

    @Provides
    fun provideInterceptor() = HeaderInterceptor()

    @Provides
    fun provideApiClient(client: GoTKtorHttpClient): DataClient = client
}