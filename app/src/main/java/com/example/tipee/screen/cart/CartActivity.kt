package com.example.tipee.screen.cart

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.tipee.base.BaseActivity
import com.example.tipee.database.AppDatabase
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.ActivityCartBinding
import com.example.tipee.screen.login.LoginActivity
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.example.tipee.utils.LoginUtils
import com.example.tipee.utils.MoneyUtils
import com.example.tipee.utils.event.BuyEvent
import com.example.tipee.utils.event.DeleteCartEvent
import com.example.tipee.utils.setEnableView
import com.example.tipee.widget.popup.AddToCartBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class CartActivity : BaseActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CartActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mBinding: ActivityCartBinding
    private lateinit var mAdapter: CartAdapter
    private lateinit var db: AppDatabase
    override fun getViewBinding(): View {
        mBinding = ActivityCartBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "Tipee"
        ).allowMainThreadQueries().build()
    }

    override fun configViews() {
        val listOrder = mutableListOf<Order>()
        mAdapter = CartAdapter(listOrder, object : CartAdapter.OnCartItemClickListener {
            override fun onEditItem(order: Order) {
                AddToCartBottomSheet(
                    order,
                    object : AddToCartBottomSheet.OnAddCartListener {
                        override fun onAddCartSuccess(order: Order) {
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(this@CartActivity, "Thành công", Toast.LENGTH_SHORT).show()
                                mAdapter.listOrder.forEach {
                                    if(it.productId == order.productId){
                                        it.quantity = order.quantity
                                        mAdapter.notifyDataSetChanged()
                                        return@forEach
                                    }
                                }
                                calculateTotalBill(listOrder)
                            }
                        }

                        override fun onAddFail() {
                            runOnUiThread {
                                Toast.makeText(this@CartActivity, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }).show(supportFragmentManager, "")
            }

            override fun onItemClick(order: Order) {
                ProductDetailActivity.start(this@CartActivity, order.productId, true)
            }

            override fun onItemDelete(position: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        db.orderDao().delete(listOrder[position])
                        EventBus.getDefault().post(DeleteCartEvent(listOrder[position]))
                        runOnUiThread {
                            listOrder.removeAt(position)
                            mAdapter.notifyItemRemoved(position)
                            calculateTotalBill(listOrder)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@CartActivity, "Có lỗi xảy ra", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        })

        mBinding.rvCart.apply {
            adapter = mAdapter
        }

        CoroutineScope(Dispatchers.IO).launch {
            listOrder.clear()
            listOrder.addAll(db.orderDao().getAll().toMutableList())
            runOnUiThread {
                mAdapter.notifyDataSetChanged()
                calculateTotalBill(listOrder)
            }
        }

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.btnSubmit.setOnClickListener {
            if(!LoginUtils.isLogin()) {
                LoginActivity.start(this)
            } else {
                if(LoginUtils.getUserBalance() < MoneyUtils.toInt(mBinding.tvTotalBill.text.toString())){
                    Toast.makeText(this, "Bạn không có đủ số dư", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Thanh toán thành công!!", Toast.LENGTH_SHORT).show()
                    LoginUtils.setUserBalance(LoginUtils.getUserBalance() - MoneyUtils.toInt(mBinding.tvTotalBill.text.toString()))
                    CoroutineScope(Dispatchers.IO).launch {
                        db.orderDao().nukeTable()
                    }
                    EventBus.getDefault().post(BuyEvent())
                    runOnUiThread{
                        finish()
                    }
                }
            }
        }
    }

    fun calculateTotalBill(listOrder: MutableList<Order>) {
        var total = 0
        listOrder.forEach {
            total+= it.price*it.quantity
        }
        mBinding.tvTotalBill.text = MoneyUtils.toVND(total)
        toggleSubmitBtn(listOrder)
    }

    private fun toggleSubmitBtn(list: List<Any>) {
        if (list.isNullOrEmpty()) {
            mBinding.btnSubmit.setEnableView(false)
            return
        }
        mBinding.btnSubmit.setEnableView(true)
    }
}