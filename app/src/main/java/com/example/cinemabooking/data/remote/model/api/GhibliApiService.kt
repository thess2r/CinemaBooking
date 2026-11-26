
package com.example.cinemabooking.data.remote.api
import com.example.cinemabooking.data.remote.model.OnlineMovie
import retrofit2.http.GET

interface GhibliApiService {

    @GET("films")
    suspend fun getFilms(): List<OnlineMovie>
}
