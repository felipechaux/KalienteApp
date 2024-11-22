package com.example.network.data

import retrofit2.Response
import retrofit2.http.GET

interface PlayerStatusService {

    @GET("status-json.xsl")
    suspend fun getStatus(): Response<PlayerStatus>
}
