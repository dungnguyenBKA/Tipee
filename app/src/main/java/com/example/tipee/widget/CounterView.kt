package com.example.tipee.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.tipee.databinding.LayoutCounterBinding

class CounterView : LinearLayout {
    private lateinit var mBinding: LayoutCounterBinding

    constructor(context: Context): super(context){
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?){
        mBinding = LayoutCounterBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {

        }
    }

    private fun initView(context: Context?){
        initView(context, null)
    }

    fun bindDataCounter(cur: Int = 1, max: Int, min: Int){
        mBinding.tvCounter.text = cur.toString()

        mBinding.ivDown.setOnClickListener {
            if(mBinding.tvCounter.text.toString().toInt()-1>=min){
                mBinding.tvCounter.text = (mBinding.tvCounter.text.toString().toInt()-1).toString()
            }
        }

        mBinding.ivUp.setOnClickListener {
            if(mBinding.tvCounter.text.toString().toInt()+1<=max){
                mBinding.tvCounter.text = (mBinding.tvCounter.text.toString().toInt()+1).toString()
            }
        }
    }
}