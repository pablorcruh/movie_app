package ec.com.pablorcruh.watch_app.main.data.remote.api

import ec.com.pablorcruh.watch_app.BuildConfig
import ec.com.pablorcruh.watch_app.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {


    @GET("trending/{type}/{time}")
    suspend fun getTrending(
        @Path("type") type: String,
        @Path("time") time: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MediaListDto ?


    @GET("{type}/{category}")
    suspend fun getMoviesAndTv(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MediaListDto ?

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = BuildConfig.API_KEY
    }
}