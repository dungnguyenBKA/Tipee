package com.example.tipee.screen.test.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemBannerBinding
import com.example.tipee.model.ProductDetail
import com.youth.banner.adapter.BannerAdapter


class ImageAdapter(mData: List<ProductDetail>) : BannerAdapter<ProductDetail, ImageAdapter.BannerViewHolder>(mData) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    class BannerViewHolder(b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(){

        }
    }

    override fun onBindView(holder: BannerViewHolder, data: ProductDetail, position: Int, size: Int) {
        holder.bind()
    }
}