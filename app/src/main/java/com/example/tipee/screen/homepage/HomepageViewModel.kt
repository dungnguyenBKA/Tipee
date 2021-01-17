package com.example.tipee.screen.homepage

import androidx.lifecycle.MutableLiveData
import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.response.HomepageItem
import com.example.tipee.network.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomepageViewModel : BaseViewModel() {
    private val api = RetrofitHelper.getInstance()
    var homepageData = MutableLiveData<List<HomepageItem>>()
    fun loadHomepage(){
        isLoading.value = true
        api.getHomepage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.value = false
                homepageData.value = it
            }, onError)
    }
}