package com.example.opgg_test.domain.usecase

import com.example.opgg_test.data.repository.impl.SummonerRepository
import com.example.opgg_test.domain.mapper.toMatchInfoList
import com.example.opgg_test.domain.mapper.toRecentGameModel
import com.example.opgg_test.domain.model.NetworkResult
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.module.DefaultDispatcher
import com.example.opgg_test.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMatchInfoUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(createData: Long) = summonerRepository.getMatchResultInfo(createData)
        .map {
            if (it.games.isNotEmpty() && it.recentChampion.isNotEmpty()) {
                NetworkResult.Success(
                        SummonerMatch.RecentGameInfo(it.toRecentGameModel()) to
                        SummonerMatch.MatchInfoList(it.toMatchInfoList())
                )
            } else {
                NetworkResult.Fail("ERROR or Empty")
            }
        }.flowOn(ioDispatcher)
}