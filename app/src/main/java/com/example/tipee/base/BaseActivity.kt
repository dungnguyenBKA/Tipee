package com.example.tipee.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.tipee.R
import com.example.tipee.utils.Utils

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getViewBinding(): View
    abstract fun initData()
    abstract fun configViews()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewBinding())
        initData()
        configViews()
    }

    open fun onRefreshing(){

    }

    open fun observableData(){

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun showError() {
        var intent: Intent? = null
        intent = if (Utils.isNetworkConnected(applicationContext)) {
            Intent(this, ErrorActivity::class.java)
        } else {
            Intent(this, ErrorActivity::class.java)
                .putExtra(ErrorActivity.ERROR_TYPE, ErrorActivity.NO_INTERNET)
        }

        startActivityForResult(intent, 229)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 229) {
            if(resultCode == RESULT_OK){
                onRefreshing()
            } else if(resultCode == RESULT_CANCELED){
                finish()
            }
        }
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