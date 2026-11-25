
package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.remote.model.OnlineMovie
import com.example.cinemabooking.data.remote.api.RetrofitClient

class OnlineMovieRepository {

    suspend fun getMovies(): List<OnlineMovie> {
        return RetrofitClient.api.getFilms()
    }
}
