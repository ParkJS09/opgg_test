package com.example.opgg_test.presenter.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.opgg_test.databinding.ItemUserInfoBinding
import com.example.opgg_test.domain.model.SummonerInfoModel
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.presenter.MainViewModel
import com.example.opgg_test.presenter.adapter.LeagueAdapter
import com.example.opgg_test.presenter.adapter.util.LeagueItemDecoration

class SummonerViewHolder(
    private val context: Context,
    private val binding: ItemUserInfoBinding,
    private val mainViewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root) {

    private val leagueAdapter by lazy {
        LeagueAdapter()
    }

    fun bind(item: SummonerMatch.SummonerInfo) {
        binding.vm = mainViewModel
        binding.item = item.summonerInfo
        binding.apply {
            rvTier.adapter = leagueAdapter
            rvTier.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            if(rvTier.itemDecorationCount < 1 ){
                rvTier.addItemDecoration(
                    LeagueItemDecoration()
                )
            }
            leagueAdapter.submitList(item.summonerInfo.leagueInfo)
        }

    }
}