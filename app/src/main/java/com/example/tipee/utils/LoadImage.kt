package com.example.tipee.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tipee.R

class LoadImage {
    companion object {
        fun loadImage(url: String, iv: ImageView) {
            Glide.with(iv.context)
                .load(url)
                .placeholder(R.drawable.item_placeholder)
                .into(iv)
        }

        fun loadImage(url: String, iv: ImageView, corner: Int) {
            Glide.with(iv.context)
                .load(url)
                .placeholder(R.drawable.item_placeholder)
                .transform(CenterCrop(), RoundedCorners(corner))
                .into(iv)
        }
    }
}