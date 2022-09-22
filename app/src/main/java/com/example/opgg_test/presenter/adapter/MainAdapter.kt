package com.example.opgg_test.presenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.opgg_test.R
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.presenter.MainViewModel
import com.example.opgg_test.presenter.adapter.viewholder.MatchInfoViewHolder
import com.example.opgg_test.presenter.adapter.viewholder.MoreLoadingViewHolder
import com.example.opgg_test.presenter.adapter.viewholder.RecentGameInfoViewHolder
import com.example.opgg_test.presenter.adapter.viewholder.SummonerViewHolder

class MainAdapter(
    private val mainViewModel: MainViewModel
) :
    ListAdapter<SummonerMatch, RecyclerView.ViewHolder>(SummonerMatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SUMMONER -> SummonerViewHolder(
                parent.context,
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_user_info,
                    parent,
                    false
                ),
                mainViewModel
            )
            VIEW_TYPE_RECENT_GAME_INFO -> RecentGameInfoViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_most_info,
                    parent,
                    false
                ),
                mainViewModel
            )
            VIEW_TYPE_MATCH -> MatchInfoViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_match_info,
                    parent,
                    false
                ),
                mainViewModel
            )
            VIEW_TYPE_LOADING -> MoreLoadingViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_more_loading,
                    parent,
                    false
                ),
            )
            else -> throw TypeCastException("IS_UNDEFIEND_VIEW_TYPE")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_SUMMONER -> {
                (holder as SummonerViewHolder).bind(getItem(position) as SummonerMatch.SummonerInfo)
            }
            VIEW_TYPE_RECENT_GAME_INFO -> {
                (holder as RecentGameInfoViewHolder).bind((getItem(position) as SummonerMatch.RecentGameInfo).recentGameInfo)
            }
            VIEW_TYPE_MATCH -> {
                (holder as MatchInfoViewHolder).bind((getItem(position) as SummonerMatch.MatchInfo).matchInfo)
            }
            VIEW_TYPE_LOADING -> {
                (holder as MoreLoadingViewHolder).bind()
            }
            else -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SummonerMatch.SummonerInfo -> {
                VIEW_TYPE_SUMMONER
            }
            is SummonerMatch.RecentGameInfo -> {
                VIEW_TYPE_RECENT_GAME_INFO
            }
            is SummonerMatch.MatchInfo -> {
                VIEW_TYPE_MATCH
            }
            is SummonerMatch.MoreLoading -> {
                VIEW_TYPE_LOADING
            }
            else -> {
                Log.e(TAG, "ITEM_VIEW_TYPE_ERROR")
            }
        }
    }

    companion object {
        private val TAG = MainAdapter::class.simpleName
        private val VIEW_TYPE_SUMMONER = 0
        private val VIEW_TYPE_RECENT_GAME_INFO = 1
        private val VIEW_TYPE_MATCH = 2
        private val VIEW_TYPE_LOADING = 3
    }
}


private class SummonerMatchDiffCallback : DiffUtil.ItemCallback<SummonerMatch>() {

    override fun areItemsTheSame(oldItem: SummonerMatch, newItem: SummonerMatch): Boolean {
        return when {
            oldItem is SummonerMatch.MatchInfo && newItem is SummonerMatch.MatchInfo -> {
                oldItem.matchInfo.gameId == newItem.matchInfo.gameId
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SummonerMatch, newItem: SummonerMatch): Boolean {
        return oldItem == newItem
    }
}