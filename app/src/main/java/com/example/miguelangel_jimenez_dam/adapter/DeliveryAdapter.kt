package com.example.miguelangel_jimenez_dam.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miguelangel_jimenez_dam.databinding.DeliveryItemBinding

class DeliveryAdapter (private var customerName : MutableList<String>,private var moneyStatus : MutableList<Boolean>) : RecyclerView.Adapter<DeliveryAdapter.ViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class ViewHolder (private val binding: DeliveryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                cName.text=customerName[position]
                if (moneyStatus[position] == true){
                    status.text="Recibido"
                }else{
                    status.text="No recibido"
                }


                val colorMap = mapOf(
                    true to Color.GREEN , false to Color.RED
                )
                status.setTextColor(colorMap[moneyStatus[position]]?: Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[moneyStatus[position]]?: Color.BLACK)
            }
        }

    }
}