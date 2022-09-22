package com.example.opgg_test.data.models

import androidx.annotation.Keep

@Keep
data class SummonerResponse(
    val summoner: SummonerInfoDto
)

@Keep
data class SummonerInfoDto(
    val name: String,
    val level: String,
    val profileImageUrl: String,
    val leagues: List<LeagueDto>,
)

@Keep
data class LeagueDto(
    val wins: Int,
    val losses: Int,
    val tierRank: TierRankDto
)

@Keep
data class TierRankDto(
    val name: String,
    val imageUrl: String,
    val tier: String,
    val lp: Int,
    val tierRankPoint: Int
)