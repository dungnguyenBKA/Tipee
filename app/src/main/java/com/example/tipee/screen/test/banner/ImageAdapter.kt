package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.R
import com.example.tipee.databinding.ItemBannerBinding
import com.example.tipee.model.ProductDetail
import com.youth.banner.adapter.BannerAdapter


class ImageAdapter(mData: List<String>) : BannerAdapter<String, ImageAdapter.BannerViewHolder>(mData) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onViewAttachedToWindow(holder: BannerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.loadAnimation()
    }

    class BannerViewHolder(var b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root) {
        private val animatorList = arrayListOf<ObjectAnimator>()
        fun bind(){
            addLayout(BlankFragment.Direction.LEFT, 200f, 1000)
            addLayout(BlankFragment.Direction.RIGHT,150f, 1500)
        }

        fun loadAnimation(){
            val handler = Handler()
            handler.postDelayed(
                Runnable {
                    animatorList.forEach { animator ->
                        animator.start()
                    }
                }, 1500
            )
        }

        private fun addLayout(direction: BlankFragment.Direction, distance: Float, _duration: Long){
            val newLayer = ImageView(b.root.context).apply {
                setImageResource(R.drawable.chest)
                adjustViewBounds = true
                elevation = animatorList.size.toFloat()
            }
            var alignParent = 0
            var dis = 0f
            if(direction == BlankFragment.Direction.LEFT){
                alignParent = RelativeLayout.ALIGN_PARENT_START
                dis = distance
            } else {
                alignParent = RelativeLayout.ALIGN_PARENT_END
                dis = -distance
            }
            val param = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                addRule(alignParent)
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            }
            b.root.addView(newLayer, param)
            val animator = ObjectAnimator.ofFloat(newLayer, "translationX", dis).apply {
                duration = _duration
            }
            animatorList.add(animator)
        }
    }

    override fun onBindView(holder: BannerViewHolder, data: String, position: Int, size: Int) {
        holder.bind()
    }


}