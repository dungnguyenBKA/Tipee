package com.example.tipee.widget.commentbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.LayoutItemCommentBinding
import com.example.tipee.model.Review
import com.example.tipee.utils.LoadImage

class CommentAdapter(var listComment: List<Review>, var listener: OnItemClickListener): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(comment: Review)
    }

    class CommentViewHolder(var b: LayoutItemCommentBinding, var listener: OnItemClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(comment: Review){
            b.root.setOnClickListener {
                listener.onItemClick(comment)
            }

            LoadImage.loadImage(comment.created_by.userAvatarUrl, b.ivAvatar)
            b.ratingBar.rating = comment.rating
            b.tvTitle.text = comment.created_by.userRealName
            b.tvContent.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(listComment[position])
    }

    override fun getItemCount(): Int {
        return listComment.size
    }
}