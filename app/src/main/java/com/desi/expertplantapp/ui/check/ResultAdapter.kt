package com.desi.expertplantapp.ui.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.databinding.ItemPlantsBinding
import java.lang.StringBuilder

class ResultAdapter(private var listPlants: ArrayList<Plant>) : RecyclerView.Adapter<ResultAdapter.PlantViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Plant)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setList(plants: ArrayList<Plant>) {
        listPlants.clear()
        listPlants.addAll(plants)
        notifyDataSetChanged()
    }

    class PlantViewHolder (private val binding: ItemPlantsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(plant: Plant) {
            with(binding) {
                tvItemName.text = plant.name
                tvItemDesc.text = plant.desc
                tvItemScore.text = StringBuilder("Certainty Percentage : ${plant.score} %")
                Glide.with(itemView.context)
                    .load(plant.image)
                    .into(imgItemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val itemPlantsBinding = ItemPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(itemPlantsBinding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = listPlants[position]
        holder.bind(plant)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPlants[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listPlants.size
}