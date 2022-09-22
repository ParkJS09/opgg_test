package com.example.opgg_test.domain.model

import android.util.Log
import androidx.paging.PagingData
import com.example.opgg_test.data.models.game.info.ChampionInfo
import com.example.opgg_test.data.models.game.info.Item
import com.example.opgg_test.data.models.game.info.Spell
import com.example.opgg_test.data.models.game.info.Stats

sealed class SummonerMatch {
    // 소환사 정보
    data class SummonerInfo(val summonerInfo: SummonerInfoModel) : SummonerMatch()

    // 모스트 정보
    data class RecentGameInfo(val recentGameInfo: RecentGameModel) : SummonerMatch()

    // 게임 매치 정보
    data class MatchInfoList(val matchList: List<MatchInfo>) : SummonerMatch()

    // 게임 매치 상세 정보
    data class MatchInfo(val matchInfo: MatchInfoModel) : SummonerMatch()

    // 게임 매치 상세 정보
    object MoreLoading : SummonerMatch()
}

data class SummonerInfoModel(
    val name: String,
    val level: String,
    val profileImageUrl: String,
    val leagueInfo: List<LeagueInfoModel>
)

data class LeagueInfoModel(
    val wins: Int,
    val losses: Int,
    val winsPer: String,
    val name: String,
    val imageUrl: String,
    val tier: String,
    val lp: String,
)

data class RecentGameModel(
    val wins: Int,
    val losses: Int,
    val kills: Double,
    val deaths: Double,
    val assists: Double,
    val kda: String,
    val winPer: String,
    val mostChamp: RecentMostChampion?,
    val mostChamp2: RecentMostChampion?,
    val mostPosition: String,
    val mostPositionPer: String
)

data class RecentMostChampion(
    val imgUrl: String,
    val winPer: Int
)

data class MatchInfoModel(
    val gameId: String,
    val champion: ChampionInfo,
    val spells: List<Spell>,
    val items: List<Item>,
    val createDate: Long,
    val createDateString: String,
    val gameType: String,
    val gameLength: String,
    val isWin: Boolean,
    val peak: List<String>,
    val stats: Stats,
)