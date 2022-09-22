package com.example.opgg_test.data.service

import com.example.opgg_test.data.models.SummonerResponse
import com.example.opgg_test.data.models.game.info.MatchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SummonerService {
    @GET("api/summoner/genetory")
    suspend fun getSummonerInfo(

    ): SummonerResponse

    @GET("api/summoner/genetory/matches?")
    suspend fun getMatchResultInfo(
        @Query("lastMatch") lastMatch : Long
    ): MatchResultResponse
}