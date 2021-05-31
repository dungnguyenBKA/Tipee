package com.example.tipee.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tipee.R

class LoadImage {
    companion object {
        fun loadImage(url: String, iv: ImageView) {
            if(url.trim().isEmpty()){
                return
            }
            if(url.contains("http")) {
                Glide.with(iv.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.item_placeholder)
                    .into(iv)
            } else {
                Glide.with(iv.context)
                    .load("https:$url")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.item_placeholder)
                    .into(iv)
            }
        }

        fun loadImage(url: String, iv: ImageView, corner: Int) {
            if(url.trim().isEmpty()){
                return
            }
            Glide.with(iv.context)
                .load(url)
                .placeholder(R.drawable.item_placeholder)
                .transform(CenterCrop(), RoundedCorners(corner))
                .into(iv)
        }
    }
}