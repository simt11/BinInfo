package com.example.bininfo.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bininfo.database.CardData
import com.example.bininfo.databinding.CardNumberItemBinding

class CardNumberAdapter(
    private val onItemClicked: (CardData) -> Unit,
) : ListAdapter<CardData, CardNumberAdapter.CardNumberViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CardData>() {
            override fun areItemsTheSame(oldItem: CardData, newItem: CardData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CardData, newItem: CardData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardNumberViewHolder {
        val viewHolder = CardNumberViewHolder(
            CardNumberItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CardNumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CardNumberViewHolder(
        private var binding: CardNumberItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cardData: CardData) {
            binding.cardNumber.text = cardData.cardNumber
        }
    }

}