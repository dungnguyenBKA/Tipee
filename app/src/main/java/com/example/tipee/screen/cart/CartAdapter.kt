package com.example.tipee.screen.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.ItemCartBinding
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.MoneyUtils

class CartAdapter(var listOrder: List<Order>, private var listener: OnCartItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnCartItemClickListener{
        fun onItemClick(order: Order)
        fun onItemDelete(position: Int)
        fun onEditItem(order: Order)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CartViewHolder){
            holder.bind(listOrder[position])
        }
    }

    override fun getItemCount() = listOrder.size

    class CartViewHolder(var b: ItemCartBinding, var listener: OnCartItemClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(order: Order){
            b.ivThumbnail.setOnClickListener {
                listener.onItemClick(order)
            }

            b.root.setOnClickListener {
                listener.onEditItem(order)
            }

            b.ivDelete.setOnClickListener {
                listener.onItemDelete(adapterPosition)
            }
            b.tvProductName.text = order.productName
            b.tvOrderId.text = order.orderId
            b.tvPrice.text = MoneyUtils.toVND(order.price*order.quantity)
            b.tvListPrice.text = MoneyUtils.toVND(order.listPrice*order.quantity)
            LoadImage.loadImage(order.thumbnailUrl, b.ivThumbnail)
        }
    }
}