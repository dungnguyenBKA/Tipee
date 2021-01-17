package com.example.tipee.screen.homepage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemCategory1Binding
import com.example.tipee.model.ProductDetail
import com.example.tipee.utils.MoneyUtils

@SuppressLint("SetTextI18n")
class Category1Adapter(var listProduct: List<ProductDetail>, var listener: OnViewClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener {
        fun onItemProductClick(productDetail: ProductDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCategory1ViewHolder(
            ItemCategory1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemCategory1ViewHolder) {
            holder.bind(listProduct[position])
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    class ItemCategory1ViewHolder(var b: ItemCategory1Binding, var listener: OnViewClickListener) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(productDetail: ProductDetail) {
            b.root.setOnClickListener {
                listener.onItemProductClick(productDetail)
            }

            b.tvDiscount.apply {
                visibility = if (productDetail.price < productDetail.list_price) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                text = MoneyUtils.disCountUtils(productDetail.price, productDetail.list_price)
            }

            b.tvPrice.text = MoneyUtils.toVND(productDetail.price)

            b.tvItemName.text = productDetail.name
        }
    }
}