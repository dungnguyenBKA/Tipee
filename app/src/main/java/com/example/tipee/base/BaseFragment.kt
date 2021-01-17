package com.example.tipee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.tipee.R

abstract class BaseFragment : Fragment() {
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) : View
    abstract fun initData()
    abstract fun configViews()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getViewBinding(inflater, container)
    }

    open fun observableData(){

    }

    open fun onRefreshing(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        configViews()
    }

    private lateinit var skeletonScreen: SkeletonScreen
    fun showLoadingScreen(rootView: View){
        skeletonScreen = Skeleton.bind(rootView)
            .load(R.layout.layout_loading)
            .show()
    }

    fun closeLoadingScreen(){
        if(this::skeletonScreen.isInitialized){
            skeletonScreen.hide()
        }
    }
}