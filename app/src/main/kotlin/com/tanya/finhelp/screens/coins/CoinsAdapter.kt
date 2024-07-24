package com.tanya.finhelp.screens.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanya.finhelp.databinding.ItemCoinBinding
import com.tanya.finhelp.domain.Coin
import com.tanya.finhelp.screens.coins.CoinsAdapter.CoinVH

class CoinsAdapter(private var onClick: (Coin) -> Unit) : ListAdapter<Coin, CoinVH>(Differ) {

    inner class CoinVH(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin) = with(binding) {
            Glide.with(root).load(coin.image).into(logoImage)
            symbolText.text = coin.symbol
            nameCompanyText.text = coin.name
            priceText.text = coin.price
            priceChangeText.text = coin.priceChange
            priceChangePercentageText.text = coin.priceChangePercentage
            root.setOnClickListener {
                onClick.invoke(coin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(inflater, parent, false)
        return CoinVH(binding)
    }

    override fun onBindViewHolder(holder: CoinVH, position: Int) {
        holder.bind(getItem(position))
    }

    object Differ : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
}
