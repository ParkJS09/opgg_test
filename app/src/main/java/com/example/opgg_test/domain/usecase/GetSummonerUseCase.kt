package com.example.opgg_test.domain.usecase

import com.example.opgg_test.data.repository.impl.SummonerRepository
import com.example.opgg_test.domain.mapper.toSummonerInfo
import com.example.opgg_test.domain.model.NetworkResult
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.module.DefaultDispatcher
import com.example.opgg_test.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() = summonerRepository.getSummonerInfo()
        .map {
            if (it.summoner.name.isNotEmpty()) {
                NetworkResult.Success(
                    listOf(
                        SummonerMatch.SummonerInfo(it.summoner.toSummonerInfo()),
                    )
                )
            } else {
                NetworkResult.Fail("NetworkError")
            }
        }.flowOn(ioDispatcher)
}