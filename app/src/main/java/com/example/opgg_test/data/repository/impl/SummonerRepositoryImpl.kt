package com.example.opgg_test.data.repository.impl

import com.example.opgg_test.data.models.SummonerResponse
import com.example.opgg_test.data.models.game.info.MatchResultResponse
import com.example.opgg_test.data.service.SummonerService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SummonerRepositoryImpl @Inject constructor(
    private val summonerService: SummonerService
): SummonerRepository{
    override fun getSummonerInfo(): Flow<SummonerResponse> = flow {
        emit(
            summonerService.getSummonerInfo()
        )
    }

    override fun getMatchResultInfo(createData: Long): Flow<MatchResultResponse> = flow {
        emit(
            summonerService.getMatchResultInfo(createData)
        )
    }
}