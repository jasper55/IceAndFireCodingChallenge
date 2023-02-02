package wagner.jasper.iceandfirecodingchallenge.common.network

import io.ktor.http.*
import javax.inject.Singleton

@Singleton
class HeaderInterceptor {
    fun getNextPage(header: Headers): Int? {
        val link = header["Link"]
        val linkSegments = link?.split(",") ?: emptyList()
        val nextLink = linkSegments.find { it.contains("rel=\"next\"") }
        return nextLink?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()
    }
}