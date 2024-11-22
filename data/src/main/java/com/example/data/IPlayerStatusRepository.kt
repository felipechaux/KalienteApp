package com.example.data

import com.example.network.base.ApiResource
import com.example.network.data.PlayerStatus


interface IPlayerStatusRepository {

    suspend fun getStatus(): ApiResource<PlayerStatus>
}
