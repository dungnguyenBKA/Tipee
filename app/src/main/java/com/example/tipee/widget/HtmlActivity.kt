package com.example.tipee.widget

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityHtmlBinding
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class HtmlActivity : BaseActivity() {
    companion object{
        const val TITLE = "title"
        const val HTML_STRING = "html_string"
        @JvmStatic
        fun start(context: Context, title: String, htmlString: String) {
            val starter = Intent(context, HtmlActivity::class.java)
                .putExtra(TITLE, title)
                .putExtra(HTML_STRING, htmlString)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityHtmlBinding
    override fun getViewBinding(): View {
        mBinding = ActivityHtmlBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        intent.extras?.apply {
            getString(TITLE)?.let { mBinding.tvTitle.text = it }
            getString(HTML_STRING)?.let { mBinding.htmlView.setHtml(it, HtmlHttpImageGetter(
                mBinding.htmlView,
                "",
                true))
            }
        }

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}