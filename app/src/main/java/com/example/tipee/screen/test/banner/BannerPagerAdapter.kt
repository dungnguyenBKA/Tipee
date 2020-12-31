package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tipee.R
import com.example.tipee.databinding.FragmentBannerBinding

class BannerPagerAdapter(fa: FragmentActivity, var data : List<String>):  FragmentStateAdapter(fa){
    var fragments = arrayListOf<Fragment>()
    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        val frag = BlankFragment.newInstance(data[position])
        fragments.add(frag)
        return frag
    }

    fun loadAnimation(position: Int){
        if(fragments[position] is BlankFragment){
            (fragments[position] as BlankFragment).loadAnimation()
        }
    }
}


class BannerPagerRecyclerAdapter(var data : List<String>) : RecyclerView.Adapter<BannerPagerRecyclerAdapter.ViewHolder>(){
    class ViewHolder(var b: FragmentBannerBinding): RecyclerView.ViewHolder(b.root){
        private val animatorList = arrayListOf<ObjectAnimator>()
        fun bind(){
            b.ivBannerBG.setImageResource(R.drawable.b1)
            addLayout(BlankFragment.Direction.LEFT, 100f, 1000, R.drawable.b2)
            addLayout(BlankFragment.Direction.RIGHT, 300f, 1000, R.drawable.b3)
        }

        fun loadAnimation(){
            animatorList.forEach { animator ->
                animator.start()
            }
        }

        fun addLayout(direction: BlankFragment.Direction, distance: Float, _duration: Long, id: Int){
            val newLayer = ImageView(b.root.context).apply {
                setImageResource(id)
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
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(alignParent)
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            if(direction == BlankFragment.Direction.LEFT){
                marginStart = -distance.toInt()
            } else {
                marginEnd = -distance.toInt()
            }
            }
            b.root.addView(newLayer, param)
            val animator = ObjectAnimator.ofFloat(newLayer, "translationX", dis).apply {
                duration = _duration
            }
            animatorList.add(animator)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = data.size
}