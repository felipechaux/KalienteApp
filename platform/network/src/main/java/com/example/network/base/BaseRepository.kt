package com.example.network.base

import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResource<T & Any> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                return ApiResource.success(body)
            }
            return ApiResource.error(response.message(), response.code())
        } catch (e: Exception) {
            return ApiResource.error(e.message ?: e.toString(), 0)
        }
    }
}
