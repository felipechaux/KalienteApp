package com.example.network.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("search?media_type=image")
    suspend fun getItems(
        @Query("q") query: String? = null,
    ): Response<com.example.network.data.Response>
}
