package com.example.tipee.screen.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.Category1Binding
import com.example.tipee.screen.main.PlaceHolderActivity

class HomepageAdapter(var listener: OnViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnViewClickListener{
        fun onTitleClick()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                Category1ViewHolder(
                    Category1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }

            else -> {
                Category1ViewHolder(
                    Category1Binding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Category1ViewHolder){
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    class Category1ViewHolder(var b: Category1Binding, var listener: OnViewClickListener) : RecyclerView.ViewHolder(b.root){
        fun bind(){
            b.rlTitle.setOnClickListener {
                listener.onTitleClick()
            }

            b.rvCate1.adapter = Category1Adapter(object : Category1Adapter.OnViewClickListener{
                override fun onViewClick() {
                    PlaceHolderActivity.start(b.root.context)
                }
            })
        }
    }
}