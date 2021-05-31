package com.example.tipee.screen.productdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.LayoutItemShopBinding
import com.example.tipee.screen.shopdetail.ShopDetailActivity
import com.example.tipee.utils.MoneyUtils
import com.example.tipee.widget.ShopView
import java.io.Serializable

data class ShopDetail(
    var store_id: Int = 0,
    var price: Int = 0,
    var name: String = "",
    var link: String = "",
    var logo: String = "",
    var product_id: String = ""
) : Serializable

class ShopAdapter(var listener: OnViewClickListener) :
    ListAdapter<ShopDetail, ShopAdapter.ItemViewHolder>(object :
        DiffUtil.ItemCallback<ShopDetail>() {
        override fun areItemsTheSame(oldItem: ShopDetail, newItem: ShopDetail): Boolean {
            return oldItem.store_id == newItem.store_id
        }

        override fun areContentsTheSame(oldItem: ShopDetail, newItem: ShopDetail): Boolean {
            return oldItem.store_id == newItem.store_id
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnViewClickListener {
        fun onItemClick(shop: ShopDetail)
    }

    class ItemViewHolder(var b: LayoutItemShopBinding, var listener: OnViewClickListener) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(shop: ShopDetail) {
            b.root.setOnClickListener {
                listener.onItemClick(shop)
            }

            b.shopView.loadShopData(shop)
            b.shopView.setOnShopClickListener(object : ShopView.OnShopViewClickListener {
                override fun onFollowClick(shop: ShopDetail) {
                    ShopDetailActivity.start(b.root.context, shop)
//                    ProductDetailActivity.start(b.root.context, shop.product_id, true)
                }

                override fun onShopDetailClick(shop: ShopDetail) {

                }
            })
            b.tvPrice.text = MoneyUtils.toVND(shop.price)
        }
    }
}