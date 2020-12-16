package com.example.tipee.screen.homepage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemCategory1Binding

@SuppressLint("SetTextI18n")
class Category1Adapter(var listener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener{
        fun onViewClick()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCategory1ViewHolder(
            ItemCategory1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemCategory1ViewHolder){
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ItemCategory1ViewHolder(var b: ItemCategory1Binding, var listener: OnViewClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(){
            b.root.setOnClickListener {
                listener.onViewClick()
            }

            b.tvItemName.text = "Sản phẩm #$adapterPosition"
        }
    }
}