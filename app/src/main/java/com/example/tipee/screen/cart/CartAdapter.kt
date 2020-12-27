package com.example.tipee.screen.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemCartBinding

class CartAdapter(var listener: OnCartItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnCartItemClickListener{
        fun onItemClick()
        fun onItemDelete(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CartViewHolder){
            holder.bind()
        }
    }

    override fun getItemCount() = 10

    class CartViewHolder(var b: ItemCartBinding, var listener: OnCartItemClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(){
            b.counterView.bindDataCounter(max = 5, min = 1)
            b.ivDelete.setOnClickListener {
                listener.onItemDelete(adapterPosition)
            }
        }
    }
}