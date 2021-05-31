package com.example.tipee.widget.commentbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.tipee.databinding.LayoutCommentBoxBinding
import com.example.tipee.model.Review
import com.example.tipee.screen.login.LoginActivity
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.LoginUtils

class CommentBox : LinearLayout {
    interface OnAddNewCommentListener{
        fun onAddNewCommentListener()
    }
    var listener : OnAddNewCommentListener? = null
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
    private var commentList = arrayListOf<Review>()
    fun bindComment(listComment: List<Review>){
        commentList.clear()
        commentList.addAll(listComment)
        mAdapter = CommentAdapter(commentList, object : CommentAdapter.OnItemClickListener {
            override fun onItemClick(comment: Review) {
                // TODO: 1/18/2021 to user detail
            }
        })

        mBinding.tvAddComment.setOnClickListener {
            if(!LoginUtils.isLogin()){
                LoginActivity.start(context)
            } else {
                listener?.onAddNewCommentListener()
            }
        }

        mBinding.rvTotalComment.apply {
            adapter = mAdapter
            isNestedScrollingEnabled = false
            overScrollMode = OVER_SCROLL_NEVER
        }

        if(LoginUtils.isLogin()){
            LoadImage.loadImage(LoginUtils.getAvatar(), mBinding.ivAvatar)
        }
    }

    fun addComment(comment: Review){
        commentList.add(0, comment)
        mAdapter.notifyItemInserted(0)
    }
}