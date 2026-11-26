
package com.example.cinemabooking.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineMovie(
    val id: String,
    val title: String,
    val description: String,
    val image: String? = null,

    @SerialName("running_time")
    val runningTime: String
)
