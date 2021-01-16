package com.example.tipee.widget.popup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.tipee.base.BaseBottomSheet
import com.example.tipee.database.AppDatabase
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.PopupAddToCartBinding
import com.example.tipee.utils.MoneyUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToCartBottomSheet(
    var order: Order,
    var listener: OnAddCartListener
) : BaseBottomSheet() {
    interface OnAddCartListener {
        fun onAddCartSuccess(order: Order)
        fun onAddFail()
    }


    private lateinit var mBinding: PopupAddToCartBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = PopupAddToCartBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private lateinit var db : AppDatabase
    override fun initData() {
         db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Tipee"
        ).build()
        mBinding.counter.bindDataCounter(order.quantity,20, 1)
    }

    override fun configViews() {
        mBinding.tvBill.text = MoneyUtils.toVND(order.quantity*order.price)
        mBinding.counter.curVal.observe(this, Observer {
            mBinding.tvBill.text = MoneyUtils.toVND(it * order.price)
            order.quantity = it
        })
        mBinding.btnSubmitAdd.setOnClickListener {
            addCart(
                order
            )
            dismiss()
        }
    }

    private fun addCart(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.orderDao().insertOrder(order)
                listener.onAddCartSuccess(order)
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onAddFail()
            }
        }
    }
}