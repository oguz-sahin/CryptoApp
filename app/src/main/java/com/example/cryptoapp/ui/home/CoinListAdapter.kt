package com.example.cryptoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.data.dto.CryptoModelItem
import com.example.cryptoapp.databinding.ItemCoinListBinding
import com.example.cryptoapp.ui.common.BaseDiffUtilItemCallback

class CoinListAdapter :
    ListAdapter<CryptoModelItem, CoinListAdapter.ViewHolder>(BaseDiffUtilItemCallback<CryptoModelItem>()) {

    private var onItemCoinClickListener: ((coinId: String) -> Unit)? = null

    fun setOnItemCoinClickListener(onItemCoinClickListener: ((coinId: String) -> Unit)?) {
        this.onItemCoinClickListener = onItemCoinClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_coin_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCoinListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemCoinClickListener?.invoke(
                    getItem(
                        adapterPosition
                    ).cryptoId
                )
            }
        }

        fun bind(cryptoModelItem: CryptoModelItem) {
            with(binding) {
                itemCoin = cryptoModelItem
                executePendingBindings()
                root.animation =
                    AnimationUtils.loadAnimation(
                        binding.root.context,
                        R.anim.animation_rv
                    )
            }
        }
    }
}