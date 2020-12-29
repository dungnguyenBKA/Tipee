package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.ItemBannerBinding
import com.example.tipee.model.ProductDetail
import com.youth.banner.adapter.BannerAdapter


class ImageAdapter(mData: List<ProductDetail>) : BannerAdapter<ProductDetail, ImageAdapter.BannerViewHolder>(mData) {
    interface OnPageChangeListener{
        fun onChange(position: Int)
    }
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    class BannerViewHolder(var b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(){
            val animator = ObjectAnimator.ofFloat(b.layer1, "translationX", 200f).apply {
                duration = 1000
            }
            val pos = adapterPosition

            animator.start()
        }
    }

    override fun onBindView(holder: BannerViewHolder, data: ProductDetail, position: Int, size: Int) {
        holder.bind()
    }
}