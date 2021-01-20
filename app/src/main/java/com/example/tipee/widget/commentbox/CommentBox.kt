package com.example.tipee.widget.commentbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.tipee.databinding.LayoutCommentBoxBinding
import com.example.tipee.model.Comment

class CommentBox : LinearLayout {
    private lateinit var mBinding: LayoutCommentBoxBinding

    constructor(context: Context): super(context){
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?){
        mBinding = LayoutCommentBoxBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {

        }
    }

    private fun initView(context: Context?){
        initView(context, null)
    }

    private lateinit var mAdapter: CommentAdapter
    fun bindComment(listComment: List<Comment>){
        mAdapter = CommentAdapter(listComment, object : CommentAdapter.OnItemClickListener {
            override fun onItemClick(comment: Comment) {
                // TODO: 1/18/2021 to user detail
            }
        })

        mBinding.tvAddComment.setOnClickListener {

        }

        mBinding.rvTotalComment.apply {
            adapter = mAdapter
        }
    }
}