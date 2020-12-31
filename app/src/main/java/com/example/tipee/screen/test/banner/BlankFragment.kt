package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.content.Context.WINDOW_SERVICE
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tipee.R
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentBannerBinding
import com.youth.banner.adapter.BannerAdapter
import kotlin.math.abs


class BlankFragment : BaseFragment() {
    enum class Direction{
        LEFT,
        RIGHT
    }
    private lateinit var mBinding: FragmentBannerBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentBannerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private var str = ""
    override fun initData() {
        arguments?.getString("url")?.let {
            str = it
        }
    }

    override fun configViews() {
        addLayoutBG()
        addLayout(Direction.LEFT, 200f, 1000, R.drawable.b2)
        addLayout(Direction.RIGHT,150f, 1500, R.drawable.b3)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString("url", param1)
                }
            }
    }
    fun addLayoutBG(){
        mBinding.ivBannerBG.setImageResource(R.drawable.b1)
    }
    fun addLayout(direction: Direction, distance: Float, _duration: Long, id: Int){
        val newLayer = ImageView(requireContext()).apply {
            setImageResource(id)
            adjustViewBounds = true
            elevation = animatorList.size.toFloat()
        }
        var alignParent = 0
        var dis = 0f
        if(direction == Direction.LEFT){
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
//            if(direction == Direction.LEFT){
//                marginStart = -distance.toInt()
//            } else {
//                marginEnd = -distance.toInt()
//            }
//            setMargins(-distance.toInt(), 0, 0, 0)
        }
        mBinding.root.addView(newLayer, param)
        val animator = ObjectAnimator.ofFloat(newLayer, "translationX", dis).apply {
            duration = _duration
        }
        animatorList.add(animator)
    }
    private val animatorList = arrayListOf<ObjectAnimator>()

    fun loadAnimation(){
        val ani = ObjectAnimator.ofFloat(mBinding.test, "translationX", 80f)
        ani.start()
        animatorList.forEach { animator ->
            animator.start()
        }
    }
}