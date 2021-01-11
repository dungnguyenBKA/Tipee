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
import com.example.tipee.model.ProductDetail
import com.example.tipee.utils.MoneyUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToCartBottomSheet(
    var productDetail: ProductDetail,
    var listener: OnAddCartListener
) : BaseBottomSheet() {
    interface OnAddCartListener {
        fun onAddCartSuccess()
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
        mBinding.counter.bindDataCounter(max = 20, min = 1)
    }

    override fun configViews() {
        val unitPrice = 100000
        mBinding.tvBill.text = MoneyUtils.toVND(unitPrice)
        mBinding.counter.curVal.observe(this, Observer {
            mBinding.tvBill.text = MoneyUtils.toVND(it * unitPrice)
        })
        mBinding.btnSubmitAdd.setOnClickListener {
            addCart(
                Order(
                    productId = productDetail.id,
                    productName = productDetail.name,
                    quantity = mBinding.counter.getCurrent(),
                    thumbnailUrl = productDetail.thumbnail_url,
                    price = 100000,
                    listPrice = 200000
                )
            )
            dismiss()
        }
    }

    private fun addCart(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.orderDao().insertOrder(order)
                listener.onAddCartSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onAddFail()
            }
        }
    }
}