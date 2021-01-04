package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.R
import com.example.tipee.databinding.ItemBannerBinding
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder


class ImageAdapter(mData: List<String>) :
    BannerAdapter<String, ImageAdapter.ViewHolder>(mData){
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        return ViewHolder(
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindView(
        holder: ImageAdapter.ViewHolder,
        data: String?,
        position: Int,
        size: Int
    ) {
        holder.bind(position)
    }

    class ViewHolder(var b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root){
        fun bind(position: Int){

        }
    }
}