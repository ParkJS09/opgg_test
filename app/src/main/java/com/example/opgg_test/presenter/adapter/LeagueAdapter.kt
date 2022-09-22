package com.example.opgg_test.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.opgg_test.R
import com.example.opgg_test.domain.model.LeagueInfoModel
import com.example.opgg_test.presenter.adapter.viewholder.LeagueInfoViewHolder

class LeagueAdapter :
    ListAdapter<LeagueInfoModel, LeagueInfoViewHolder>(LeagueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueInfoViewHolder {
        return LeagueInfoViewHolder(
            parent.context,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_league_info,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LeagueInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class LeagueDiffCallback : DiffUtil.ItemCallback<LeagueInfoModel>() {
        override fun areItemsTheSame(oldItem: LeagueInfoModel, newItem: LeagueInfoModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: LeagueInfoModel,
            newItem: LeagueInfoModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
