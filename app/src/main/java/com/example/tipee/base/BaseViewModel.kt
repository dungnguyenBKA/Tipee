package com.example.tipee.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.functions.Consumer

open class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Boolean>()
    var err = MutableLiveData<Throwable>()
    var onError = Consumer<Throwable>(){
        isError.value = true
        err.value = it
        isLoading.value = false
    }
}