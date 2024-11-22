package com.example.network.data



data class Response(
    val collection: Collection?,
)

data class Collection(
    val items: List<String>?,
)
