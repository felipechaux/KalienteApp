package com.example.videoplayermanager.usecases

import com.example.data.PlayerStatusRepository
import javax.inject.Inject

class GetPlayerStatusUseCase @Inject constructor(private val repository: PlayerStatusRepository) {

    suspend operator fun invoke() = repository.getStatus()
}
