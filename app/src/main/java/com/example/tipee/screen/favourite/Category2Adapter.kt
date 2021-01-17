package com.example.tipee.screen.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemFavouriteProductBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.MoneyUtils

class Category2Adapter(var listProduct: List<ProductDetail>, var listener: OnViewClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener {
        fun onItemProductClick(productDetail: ProductDetail)
        fun onItemDelete(productDetail: ProductDetail, adapterPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCategory1ViewHolder(
            ItemFavouriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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

    class ItemCategory1ViewHolder(var b: ItemFavouriteProductBinding, var listener: OnViewClickListener) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(productDetail: ProductDetail) {
            b.root.setOnClickListener {
                listener.onItemProductClick(productDetail)
            }

            b.ivDelete.setOnClickListener {
                listener.onItemDelete(productDetail, adapterPosition)
            }

            b.tvListPrice.apply {
                visibility = if (productDetail.price < productDetail.list_price) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                text = MoneyUtils.disCountUtils(productDetail.price, productDetail.list_price)
            }

            LoadImage.loadImage(productDetail.thumbnail_url, b.ivThumbnail)

            b.tvPrice.text = MoneyUtils.toVND(productDetail.price)

            b.tvProductName.text = productDetail.name
        }
    }
}