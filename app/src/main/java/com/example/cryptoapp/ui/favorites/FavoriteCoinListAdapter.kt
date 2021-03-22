package com.example.cryptoapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.data.model.CoinDetailModel
import com.example.cryptoapp.databinding.ItemFavoriteCoinBinding
import com.example.cryptoapp.ui.common.BaseDiffUtilItemCallback

class FavoriteCoinListAdapter :
    ListAdapter<CoinDetailModel, FavoriteCoinListAdapter.ViewHolder>(BaseDiffUtilItemCallback<CoinDetailModel>()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite_coin,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ItemFavoriteCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coinDetailModel: CoinDetailModel) {
            with(binding) {
                itemCoin = coinDetailModel
                executePendingBindings()
            }
        }
    }
}
