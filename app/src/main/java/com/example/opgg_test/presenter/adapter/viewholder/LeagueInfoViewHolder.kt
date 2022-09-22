package com.example.opgg_test.presenter.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opgg_test.databinding.ItemLeagueInfoBinding
import com.example.opgg_test.domain.model.LeagueInfoModel

class LeagueInfoViewHolder(
    private val context: Context,
    private val binding: ItemLeagueInfoBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LeagueInfoModel) {
        binding.apply {
            Glide.with(context)
                .load(item.imageUrl)
                .circleCrop()
                .into(ivTierThumbnail)

            tvLeagueName.text = item.name
            tvLeagueTier.text = item.tier
            tvLeaguePoint.text = item.lp
            tvLeagueWlp.text = item.winsPer
        }
    }
}