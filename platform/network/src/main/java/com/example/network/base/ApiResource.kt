package com.example.network.base

data class ApiResource<out T>(
    val status: Status,
    val data: T?,
    val message: String? = null,
    val code: Int? = null,
) {

    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T?): ApiResource<T> {
            return ApiResource(Status.SUCCESS, data, null, 0)
        }

        fun <T> error(message: String?, code: Int?, data: T? = null): ApiResource<T> {
            return ApiResource(Status.ERROR, data, message, code)
        }
    }
}
