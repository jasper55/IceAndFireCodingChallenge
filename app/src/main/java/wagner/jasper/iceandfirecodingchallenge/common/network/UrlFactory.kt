package wagner.jasper.iceandfirecodingchallenge.common.network

class UrlFactory {

    fun getCharacterId(url: String) = getId(url)

    private fun getId(url: String) = url.substringAfterLast("/").toInt()
}
