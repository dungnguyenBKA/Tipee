package com.example.tipee.screen.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tipee.R
import com.example.tipee.databinding.ItemImageBinding
import com.example.tipee.utils.LoadImage
import com.youth.banner.adapter.BannerAdapter




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
            holder.bind(listUrls[position])
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

class ImageAdapterBanner(list: List<String>) :
    BannerAdapter<String, ImageAdapterBanner.BannerViewHolder?>(list) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    class BannerViewHolder(view: ImageView) :
        RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }

    override fun onBindView(holder: BannerViewHolder?, data: String, position: Int, size: Int) {
        holder?.imageView?.let { LoadImage.loadImage(data, it)}
    }
}