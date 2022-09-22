package com.example.opgg_test.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.example.opgg_test.data.models.LeagueDto
import com.example.opgg_test.data.models.SummonerInfoDto
import com.example.opgg_test.data.models.game.info.GameInfo
import com.example.opgg_test.data.models.game.info.MatchResultResponse
import com.example.opgg_test.domain.model.*
import java.util.concurrent.TimeUnit

fun SummonerInfoDto.toSummonerInfo() =
    SummonerInfoModel(this.name, this.level, this.profileImageUrl, this.leagues.map {
        it.toLeagueInfoList()
    }
    )

private fun LeagueDto.toLeagueInfoList() = LeagueInfoModel(
    wins = this.wins,
    losses = this.losses,
    winsPer = "${this.wins}승 ${this.losses}패 (${getWinsPercent(this.wins, this.losses)})",
    name = this.tierRank.name,
    imageUrl = this.tierRank.imageUrl,
    tier = this.tierRank.tier,
    lp = "${this.tierRank.lp} LP"
)

private fun getWinsPercent(wins: Int, losses: Int): String =
    "${((wins.toDouble() / (wins + losses)) * 100).toInt()}%"

private fun getWinsPercentInt(wins: Int, losses: Int): Int =
    ((wins.toDouble() / (wins + losses)) * 100).toInt()


fun MatchResultResponse.toRecentGameModel(): RecentGameModel {
    val matchResult = this.copy(
        recentPosition = this.recentPosition.sortedBy {
            it.games
        }.reversed()
    )
    var wins = 0
    var losses = 0
    var kills = 0
    var deaths = 0
    var assists = 0
    var mostChamp = RecentMostChampion(
        imgUrl = matchResult.recentChampion.first().imageUrl,
        winPer = getWinsPercentInt(
            matchResult.recentChampion.first().wins,
            matchResult.recentChampion.first().losses
        )
    )
    var mostChamp2 = if (matchResult.recentChampion.size >= 2) {
        RecentMostChampion(
            imgUrl = matchResult.recentChampion[1].imageUrl,
            winPer = getWinsPercentInt(
                matchResult.recentChampion[1].wins,
                matchResult.recentChampion[1].losses
            )
        )
    } else {
        null
    }
    var mostPosition = matchResult.recentPosition.first().position
    var mostPositionGame = 0
    var mostPositionWin = 0
    var mostPositionLose = 0

    matchResult.recentChampion.forEachIndexed { index, recentChampion ->
        wins += recentChampion.wins
        losses += recentChampion.losses
        kills += recentChampion.kills
        deaths += recentChampion.deaths
        assists += recentChampion.assists
    }

    matchResult.recentPosition.forEach {
        mostPositionGame += it.games
        mostPositionWin += it.wins
        mostPositionLose += it.losses
    }

    var kda = "${(kills + assists / deaths)}: 1"
    var winPer = getWinsPercent(wins, losses)
    var positionPercent = getWinsPercent(
        mostPositionWin,
        mostPositionLose
    )

    return RecentGameModel(
        wins,
        losses,
        (kills / wins + losses).toDouble(),
        (deaths / wins + losses).toDouble(),
        (assists / wins + losses).toDouble(),
        kda,
        winPer,
        mostChamp,
        mostChamp2,
        mostPosition,
        positionPercent
    )
}

fun MatchResultResponse.toMatchInfoList(): List<SummonerMatch.MatchInfo> {
    val currentTime = System.currentTimeMillis()
    return this.games.map {
        SummonerMatch.MatchInfo(
            it.toMatchInfo(
                calculationPlayTime(it.gameLength),
                calculationTime((currentTime / 1000 - it.createDate))
            )
        )
    }
}

fun PagingData<GameInfo>.toMatchInfoListFromPaging(): PagingData<SummonerMatch.MatchInfo> {
    val currentTime = System.currentTimeMillis()
    return this.map {
        SummonerMatch.MatchInfo(
            it.toMatchInfo(
                calculationPlayTime(it.gameLength),
                calculationTime((currentTime / 1000 - it.createDate))
            )
        )
    }
}

private fun GameInfo.toMatchInfo(gameLength: String, createDateString: String) = MatchInfoModel(
    this.gameId,
    this.champion,
    this.spells,
    this.items,
    this.createDate,
    createDateString = createDateString,
    this.gameType,
    gameLength = gameLength,
    this.isWin,
    this.peak,
    this.stats
)

fun calculationPlayTime(playTime: Long): String {
    val milliseconds: Long = playTime * 1000
    val minutes = milliseconds / 1000 / 60 % 60
    val seconds = milliseconds / 1000 % 60
    return String.format("%02d:%02d", minutes, seconds)
}

fun calculationTime(createDateTime: Long): String {
    val milliseconds = createDateTime * 1000
    val diffValue = milliseconds / 1000 / 60
    return when {
        diffValue < 1 -> {
            "방금 전"
        }
        diffValue < 60 -> { //59분 보다 적다면
            TimeUnit.MINUTES.toMinutes(diffValue).toString() + "분 전"
        }
        diffValue < 1380 -> { //23시간 보다 적다면
            TimeUnit.MINUTES.toHours(diffValue).toString() + "시간 전"
        }
        diffValue < 10080 -> { //7일 보다 적다면
            TimeUnit.MINUTES.toDays(diffValue).toString() + "일 전"
        }
        diffValue < 30240 -> { //3주 보다 적다면
            (TimeUnit.MINUTES.toDays(diffValue) / 7).toString() + "주 전"
        }
        diffValue < 525600 -> { //12개월 보다 적다면
            (TimeUnit.MINUTES.toDays(diffValue) / 30).toString() + "달 전"
        }
        else -> {
            "몇 년전"
        }
    }
}