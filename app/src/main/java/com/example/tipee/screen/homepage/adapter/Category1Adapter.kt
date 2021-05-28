package com.example.tipee.screen.homepage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemCategory1Binding
import com.example.tipee.model.ProductDetail
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.MoneyUtils

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

            b.tvPrice.text = "Từ ${MoneyUtils.toVND(productDetail.price)}"

            b.tvItemName.text = productDetail.name

            LoadImage.loadImage(productDetail.thumbnail_url, b.ivItem)
        }
    }
}

class ProductAdapter(var listener: OnViewClickListener): ListAdapter<ProductDetail, ProductAdapter.ItemViewHolder>(object : DiffUtil.ItemCallback<ProductDetail>(){
    override fun areItemsTheSame(oldItem: ProductDetail, newItem: ProductDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductDetail, newItem: ProductDetail): Boolean {
        return oldItem.id == newItem.id
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCategory1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnViewClickListener {
        fun onItemProductClick(productDetail: ProductDetail)
    }

    class ItemViewHolder(var b: ItemCategory1Binding, var listener: OnViewClickListener) :
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

            b.tvPrice.text = "Từ ${MoneyUtils.toVND(productDetail.price)}"

            b.tvItemName.text = productDetail.name

            LoadImage.loadImage(productDetail.thumbnail_url, b.ivItem)
        }
    }
}