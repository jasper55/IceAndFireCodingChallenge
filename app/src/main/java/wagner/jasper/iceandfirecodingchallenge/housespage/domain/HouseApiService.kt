package wagner.jasper.iceandfirecodingchallenge.housespage.domain

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import wagner.jasper.iceandfirecodingchallenge.housespage.data.model.HouseDTO

interface HouseApiService {

    @GET("houses")
    suspend fun getHouses(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): List<HouseDTO>

    @GET("houses/{id}")
    suspend fun getHouseForId(@Path("id") id: String): HouseDTO?
}

