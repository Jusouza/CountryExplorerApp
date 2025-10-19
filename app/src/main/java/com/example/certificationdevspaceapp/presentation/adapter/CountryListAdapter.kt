package com.example.certificationdevspaceapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.certificationdevspaceapp.R
import com.example.certificationdevspaceapp.databinding.ItemListBinding
import com.example.certificationdevspaceapp.presentation.data.CountryUiState

class CountryListAdapter(
    private val onItemClick: (CountryUiState) -> Unit,
    private val onFavoriteClick: (CountryUiState) -> Unit
) : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private val items = mutableListOf<CountryUiState>()

    fun submitList(newList: List<CountryUiState>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(private val binding: ItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CountryUiState) {
            binding.countryNameTv.text = item.name
            binding.continentTv.text = item.capital
            Log.d("ADAPTER", "Binding ${item.name} (${item.code})")

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
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}