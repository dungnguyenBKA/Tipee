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
        RIGHT,
        UP,
        DOWN
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
        addLayout(Direction.LEFT, 200f, 1000)
        addLayout(Direction.RIGHT,150f, 1500)
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
    fun addLayout(direction: Direction, distance: Float, _duration: Long){
        val newLayer = ImageView(requireContext()).apply {
            setImageResource(R.drawable.chest)
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
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            addRule(alignParent)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }
        mBinding.root.addView(newLayer, param)
        val animator = ObjectAnimator.ofFloat(newLayer, "translationX", dis).apply {
            duration = _duration
        }
        animatorList.add(animator)
    }
    private val animatorList = arrayListOf<ObjectAnimator>()

    fun loadAnimation(){
        animatorList.forEach { animator ->
            animator.start()
        }
    }

    fun reverseAnimation(){
//        animatorList.forEach { animator ->
//            animator.interpolator = Interpolator { abs(it -1f) }
//            animator.start()
//        }
    }
}