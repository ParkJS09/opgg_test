package com.example.opgg_test.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opgg_test.domain.model.*
import com.example.opgg_test.domain.usecase.GetMatchInfoUseCase
import com.example.opgg_test.domain.usecase.GetSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSummonerUseCase: GetSummonerUseCase,
    private val getMatchInfoUseCase: GetMatchInfoUseCase,
) : ViewModel() {

    private val _viewModelState = MutableStateFlow(MainViewModelState(isLoading = true))
    val uiState = _viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _viewModelState.value.toUiState()
        )

    fun getAllData() {
        viewModelScope.launch {
            _viewModelState.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                )
            }
            getSummonerUseCase().combine(getMatchInfoUseCase(System.currentTimeMillis() / 1000)) { summoner, matchResult ->
                if (summoner is NetworkResult.Success && matchResult is NetworkResult.Success) {
                    val (recent, match) = matchResult.response
                    summoner.response + recent + match.matchList
                } else {
                    throw Exception("리스트 조회 에러 발생")
                }
            }.catch { error ->
                _viewModelState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        noticeMsg = error.toString()
                    )
                }
            }.collect { list ->
                _viewModelState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        infoList = list,
                        lastCreateDate = if (list.last() is SummonerMatch.MatchInfo) {
                            (list.last() as SummonerMatch.MatchInfo).matchInfo.createDate
                        } else {
                            0L
                        }
                    )
                }
            }
        }
    }


    fun getMoreMatchData() {
        if (_viewModelState.value.lastCreateDate != 0L && !_viewModelState.value.isMoreLoading) {
            viewModelScope.launch {
                _viewModelState.update {
                    it.copy(
                        isMoreLoading = true,
                        infoList = it.infoList + SummonerMatch.MoreLoading
                    )
                }
                getMatchInfoUseCase(
                    _viewModelState.value.lastCreateDate
                ).catch { error ->
                    _viewModelState.update {
                        it.copy(
                            isMoreLoading = false,
                            noticeMsg = error.toString()
                        )
                    }
                }.collect { matchResult ->
                    when (matchResult) {
                        is NetworkResult.Success -> {
                            val match = matchResult.response.second
                            _viewModelState.update { uiState ->
                                uiState.copy(
                                    isMoreLoading = false,
                                    infoList = uiState.infoList - SummonerMatch.MoreLoading + match.matchList,
                                    lastCreateDate = if (match.matchList.last() is SummonerMatch.MatchInfo) {
                                        match.matchList.last().matchInfo.createDate
                                    } else {
                                        0L
                                    }
                                )
                            }
                        }
                        is NetworkResult.Fail -> {
                            _viewModelState.update {
                                it.copy(
                                    isMoreLoading = false,
                                    noticeMsg = "정보를 가져오는 중 에러가 발생하였습니다."
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface MainUiState {
    val isLoading: Boolean
    val isError: Boolean
    val noticeMsg: String

    data class EmptyInfo(
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val noticeMsg: String
    ) : MainUiState

    data class SummonerInfo(
        override val isLoading: Boolean,
        override val isError: Boolean,
        val isMoreLoading: Boolean,
        override val noticeMsg: String,
        val infoList: List<SummonerMatch>,
        val lastCreateDate: Long = 0L
    ) : MainUiState
}

data class MainViewModelState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isMoreLoading: Boolean = false,
    val noticeMsg: String = "",
    val infoList: List<SummonerMatch> = emptyList(),
    val lastCreateDate: Long = 0L
) {
    fun toUiState(): MainUiState = if (infoList.isEmpty()) {
        MainUiState.EmptyInfo(
            isLoading = isLoading,
            isError = isError,
            noticeMsg = noticeMsg,
        )
    } else {
        MainUiState.SummonerInfo(
            isLoading = isLoading,
            isError = isError,
            isMoreLoading = isMoreLoading,
            noticeMsg = noticeMsg,
            infoList = infoList,
            lastCreateDate = lastCreateDate
        )
    }
}