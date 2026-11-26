
package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.remote.model.OnlineMovie

interface OnlineMoviesDataSource {
    suspend fun getMovies(): List<OnlineMovie>
}
