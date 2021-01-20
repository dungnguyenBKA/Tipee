package com.example.tipee.widget.commentbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tipee.databinding.LayoutItemCommentBinding
import com.example.tipee.model.Comment
import com.example.tipee.utils.LoadImage

class CommentAdapter(var listComment: List<Comment>, var listener: OnItemClickListener): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(comment: Comment)
    }

    class CommentViewHolder(var b: LayoutItemCommentBinding, var listener: OnItemClickListener): RecyclerView.ViewHolder(b.root){
        fun bind(comment: Comment){
            b.root.setOnClickListener {
                listener.onItemClick(comment)
            }

            LoadImage.loadImage(comment.user_avatar, b.ivAvatar)
            b.ratingBar.rating = comment.rating
            b.tvParentUserName.text = comment.userRealName
            b.tvParentComment.text = comment.comment
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