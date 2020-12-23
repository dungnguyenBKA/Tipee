package com.example.tipee.screen.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tipee.R
import com.example.tipee.databinding.ItemImageBinding

class ImageAdapter(var onViewClickListener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: MutableList<String> = arrayListOf()
    interface OnViewClickListener{
        fun onViewClick()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onViewClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ImageViewHolder){
            holder.bind()
        }
    }

    override fun getItemCount() = 10

    class ImageViewHolder(var b: ItemImageBinding, var onViewClickListener: OnViewClickListener) : RecyclerView.ViewHolder(b.root){
        fun bind(){
//            Glide.with(b.root.context)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.item_placeholder)
//                .into(b.ivItem)

            b.root.setOnClickListener {
                onViewClickListener.onViewClick()
            }
        }
    }
}