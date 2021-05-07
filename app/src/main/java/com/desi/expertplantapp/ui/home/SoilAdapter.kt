package com.desi.expertplantapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.ItemSoilsBinding

class SoilAdapter(private val listSoils : ArrayList<Soil>) : RecyclerView.Adapter<SoilAdapter.SoilViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Soil)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class SoilViewHolder(private val binding: ItemSoilsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(soil: Soil) {
            with(binding) {
                tvItemName.text = soil.name
                Glide.with(itemView.context)
                        .load(soil.image)
                        .into(imgItemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoilViewHolder {
        val itemsSoilsBinding = ItemSoilsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SoilViewHolder(itemsSoilsBinding)
    }

    override fun onBindViewHolder(holder: SoilViewHolder, position: Int) {
        val soil = listSoils[position]
        holder.bind(soil)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listSoils[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listSoils.size
}