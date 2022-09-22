package com.example.opgg_test.data.models.game.info

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MatchResultResponse(
    val games: List<GameInfo>,
    @SerializedName("champions")
    val recentChampion: List<RecentChampion>,
    @SerializedName("positions")
    val recentPosition: List<RecentPosition>,
)

@Keep
data class GameInfo(
    val gameId: String,
    val champion: ChampionInfo,
    val spells: List<Spell>,
    val items: List<Item>,
    val createDate: Long,
    val gameType: String,
    val gameLength: Long,
    val isWin: Boolean,
    val peak: List<String>,
    val stats: Stats,
)

@Keep
data class ChampionInfo(
    val imageUrl: String,
    val level: Int
)

@Keep
data class Spell(
    val imageUrl: String
)

@Keep
data class Item(
    val imageUrl: String
)

@Keep
data class Stats(
    val general: General,
    val ward: Ward
)

@Keep
data class General(
    val kill: Int,
    val death: Int,
    val assist: Int,
    val opScoreBadge: String,
    val contributionForKillRate: String,
    @SerializedName("largestMultiKillString")
    val multikillBadge: String
)

@Keep
data class Ward(
    val sightWardsBought: Int,
    val visionWardsBought: Int
)

@Keep
data class RecentChampion(
    val id: Int,
    val key: String,
    val name: String,
    val imageUrl: String,
    val games: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val wins: Int,
    val losses: Int
)

@Keep
data class RecentPosition(
    val games: Int,
    val wins: Int,
    val losses: Int,
    val position: String,
    val positionName: String,
)