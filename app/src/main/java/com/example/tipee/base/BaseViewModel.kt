package com.example.tipee.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.functions.Consumer

open class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Boolean>()
    var onError = Consumer<Throwable>(){
        isError.value = true
        isLoading.value = false
    }
}