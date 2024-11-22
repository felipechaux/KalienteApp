package com.example.data

import com.example.network.base.ApiResource
import com.example.network.base.BaseRepository
import com.example.network.data.PlayerStatus
import com.example.network.data.PlayerStatusService
import javax.inject.Inject

class PlayerStatusRepository @Inject constructor(
    private val service: PlayerStatusService,
) : IPlayerStatusRepository, BaseRepository() {
    override suspend fun getStatus(): ApiResource<PlayerStatus> {
        return getResult { service.getStatus() }
    }
}
