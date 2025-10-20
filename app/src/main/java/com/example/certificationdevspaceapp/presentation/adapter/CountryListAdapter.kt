package com.example.certificationdevspaceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.certificationdevspaceapp.R
import com.example.certificationdevspaceapp.databinding.ItemListBinding
import com.example.certificationdevspaceapp.presentation.data.CountryUiState

class CountryListAdapter(
    private val onItemClick: (CountryUiState) -> Unit,
    private val onFavoriteClick: (CountryUiState) -> Unit
) : ListAdapter<CountryUiState, CountryListAdapter.CountryViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<CountryUiState>() {
        override fun areItemsTheSame(oldItem: CountryUiState, newItem: CountryUiState) =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: CountryUiState, newItem: CountryUiState) =
            oldItem == newItem
    }

    inner class CountryViewHolder(private val binding: ItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CountryUiState) {
            binding.countryNameTv.text = item.name
            binding.continentTv.text = item.capital

            Glide.with(binding.root)
                .load(item.flagUrl)
                .into(binding.flagImg)

            binding.favoriteIcon.setImageResource(
                if (item.isFavorite) R.drawable.ic_favorited
                else R.drawable.ic_unfavorite
            )

            binding.favoriteIcon.setOnClickListener { onFavoriteClick(item) }
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}