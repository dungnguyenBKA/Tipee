package com.example.tipee.screen.homepage

import androidx.lifecycle.MutableLiveData
import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.response.TabItem
import com.example.tipee.network.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomepageViewModelV2: BaseViewModel() {
    private val api = RetrofitHelper.getTikiApiInstance()

    var tabItem = MutableLiveData<TabItem>()
    fun getTabItems(tab: Int, offSet: Int, isLoadEffect : Boolean = true){
        if(isLoadEffect) isLoading.value = true
        api.getHomeTab(tab, offSet).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tabItem.value = it
                isLoading.value = false
            }, {
                isError.value = true
                isLoading.value = false
            })
    }
}