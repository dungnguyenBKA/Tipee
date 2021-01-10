package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.R
import com.example.tipee.databinding.ItemBannerBinding
import com.example.tipee.widget.banner.BannerItemHolder
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder


class ImageAdapter(mData: List<String>) :
    BannerAdapter<String, ImageAdapter.ViewHolder>(mData){
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val vh = ViewHolder(
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        return vh
    }

    override fun onBindView(
        holder: ImageAdapter.ViewHolder,
        data: String?,
        position: Int,
        size: Int
    ) {
        holder.bind(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.runAnimation()
    }

    class ViewHolder(var b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root){
        val ani = arrayListOf<ObjectAnimator>()
        fun bind(position: Int){
            b.addView.removeAllViews()
            ani.clear()
            b.ivBannerBG.setImageResource(R.drawable.b4)
            addLayout(R.drawable.b2, "right", 1620f, 1000)
            addLayout(R.drawable.b3, "right", 1620f, 2000)
        }

        fun runAnimation(){
            ani.forEach {
                it.start()
                Log.d("banner2", "runAnimation: ")
            }
        }

        private fun addLayout(
            url: Int,
            direction: String,
            distance: Float,
            durationVal: Long
        ) {
            val newLayer = ImageView(b.root.context).apply {
                adjustViewBounds = true
                elevation = 2f
                setImageResource(url)
            }

            val moveDistance: Float
            val marginStartVal: Int
            val marginEndVal: Int

            when (direction) {
                "left" -> {
                    marginStartVal = -distance.toInt()
                    marginEndVal = distance.toInt()
                    moveDistance = distance
                }

                "right" -> {
                    marginStartVal = distance.toInt()
                    marginEndVal = -distance.toInt()
                    moveDistance = -distance
                }

                else -> {
                    marginStartVal = 0
                    marginEndVal = 0
                    moveDistance = 0f
                }
            }

            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                marginStart = marginStartVal
                marginEnd = marginEndVal
            }
            b.addView.addView(newLayer, param)
            val animator = ObjectAnimator.ofFloat(newLayer, "translationX", moveDistance).apply {
                duration = durationVal
            }
            ani.add(animator)
        }
    }
}