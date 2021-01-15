package com.example.tipee.screen.shopdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemCategory2Binding
import com.example.tipee.model.ProductDetail
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.MoneyUtils

@SuppressLint("SetTextI18n")
class ShopItemAdapter(var listener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener{
        fun onViewClick(productDetail: ProductDetail)
    }

    private var listProduct = mutableListOf<ProductDetail>()

    fun updateData(mListProduct: List<ProductDetail>){
        listProduct.clear()
        listProduct.addAll(mListProduct)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCategory1ViewHolder(
            ItemCategory2Binding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemCategory1ViewHolder){
            holder.bind(listProduct[position])
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    class ItemCategory1ViewHolder(var b: ItemCategory2Binding, var listener: OnViewClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(productDetail: ProductDetail) {
            b.root.setOnClickListener {
                listener.onViewClick(productDetail)
            }

            b.tvItemName.text = productDetail.name
            b.tvPrice.text = MoneyUtils.toVND(productDetail.price)
            LoadImage.loadImage(productDetail.thumbnail_url, b.ivItem)
        }
    }
}