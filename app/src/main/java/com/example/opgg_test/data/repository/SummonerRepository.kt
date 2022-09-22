package com.example.opgg_test.data.repository.impl


import com.example.opgg_test.data.models.SummonerResponse
import com.example.opgg_test.data.models.game.info.MatchResultResponse
import kotlinx.coroutines.flow.Flow

interface SummonerRepository {
    fun getSummonerInfo(): Flow<SummonerResponse>
    fun getMatchResultInfo(createData: Long): Flow<MatchResultResponse>
}