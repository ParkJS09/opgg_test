package com.example.opgg_test.presenter.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.opgg_test.databinding.ItemMostInfoBinding
import com.example.opgg_test.domain.model.RecentGameModel
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.presenter.MainViewModel

class RecentGameInfoViewHolder(
    private val binding: ItemMostInfoBinding,
    private val mainViewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentGameModel) {
        binding.item = item
    }
}