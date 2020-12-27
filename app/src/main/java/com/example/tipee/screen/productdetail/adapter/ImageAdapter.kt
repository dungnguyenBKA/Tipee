package com.example.tipee.screen.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tipee.R
import com.example.tipee.databinding.ItemImageBinding

class ImageAdapter(var onViewClickListener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listUrls: MutableList<String> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
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
            // TODO: 12/27/2020 regex to  1000x1000
            holder.bind(listUrls[position].replace("280x280", "1000x1000"))
        }
    }

    override fun getItemCount() = listUrls.size

    class ImageViewHolder(var b: ItemImageBinding, var onViewClickListener: OnViewClickListener) : RecyclerView.ViewHolder(b.root){
        fun bind(url: String){
            Glide.with(b.root.context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.item_placeholder)
                .into(b.ivItem)

            b.root.setOnClickListener {
                onViewClickListener.onViewClick()
            }
        }
    }
}