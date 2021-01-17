package com.example.tipee.screen.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.Category1Binding
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.response.HomepageItem
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.example.tipee.widget.StartSnapHelper

class HomepageAdapter(var homepageData: ArrayList<HomepageItem>, var listener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener{
        fun onTitleClick(idCategory: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                Category1ViewHolder(
                    Category1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }

            else -> {
                Category1ViewHolder(
                    Category1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Category1ViewHolder){
            holder.bind(homepageData[position])
        }
    }

    override fun getItemCount(): Int {
        return homepageData.size
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    class Category1ViewHolder(var b: Category1Binding, var listener: OnViewClickListener) : RecyclerView.ViewHolder(b.root){
        fun bind(homepageItem: HomepageItem){

            b.tvCategoryName.text = homepageItem.name

            b.rlTitle.setOnClickListener {
                listener.onTitleClick(homepageItem.id_category)
            }

            val snap = StartSnapHelper()
            b.rvCate1.setOnFlingListener(null);
            snap.attachToRecyclerView(b.rvCate1)
            b.rvCate1.adapter = Category1Adapter(homepageItem.product, object : Category1Adapter.OnViewClickListener {
                override fun onItemProductClick(productDetail: ProductDetail) {
                    ProductDetailActivity.start(b.root.context, productDetail.id)
                }
            })
        }
    }
}