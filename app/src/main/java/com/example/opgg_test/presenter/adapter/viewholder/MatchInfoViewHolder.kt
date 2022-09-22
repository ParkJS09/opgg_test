package com.example.opgg_test.presenter.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.opgg_test.databinding.ItemMatchInfoBinding
import com.example.opgg_test.domain.model.MatchInfoModel
import com.example.opgg_test.domain.model.SummonerMatch
import com.example.opgg_test.presenter.MainViewModel

class MatchInfoViewHolder(
    private val binding: ItemMatchInfoBinding,
    private val mainViewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MatchInfoModel) {
        binding.item = item
    }
}