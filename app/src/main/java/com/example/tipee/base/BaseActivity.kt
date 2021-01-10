package com.example.tipee.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tipee.utils.Utils
import io.reactivex.rxjava3.functions.Consumer
import org.greenrobot.eventbus.EventBus
import java.io.Serializable

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
        if (requestCode == 229 && resultCode == RESULT_OK) {
            onRefreshing()
        }
    }
}