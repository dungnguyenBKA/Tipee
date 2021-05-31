package com.example.tipee.screen.homepage

import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.ProductDetail
import com.example.tipee.network.RetrofitHelper
import com.example.tipee.network.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup

class SearchViewModel : BaseViewModel() {
    private val api = RetrofitHelper.getInstance()
    var listItemSearch = SingleLiveEvent<List<ProductDetail>>()
    fun getSearchData(kw: String){
        isLoading.value = true
        api.search(kw).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { resBody ->
                if (!resBody.isSuccessful) {
                    isLoading.value = false
                    err.value = Exception("${resBody.code()}")
                }
                resBody.body()?.string()?.let {
                    parseFromHtml(it)
                }
            }
            .subscribe({
                isLoading.value = false
                listItemSearch.value = it
            }, {
                isLoading.value = false
                err.value = it
            })
    }

    private fun parseFromHtml(html: String): List<ProductDetail>? {
        if (html.isEmpty()) {
            return null
        }
        try {
            val listItem = mutableListOf<ProductDetail>()
            val listItemElements = Jsoup.parse(html).getElementsByClass("product-item")
            listItemElements.forEach { ele ->
                val pro = ProductDetail()
                val link = ele.attr("href")
                pro.id = link.subSequence(link.indexOf("-p")+2, link.indexOf(".html")).toString()
                ele.getElementsByClass("thumbnail")[0].getElementsByTag("img").run {
                    pro.thumbnail_url = attr("src")
                }
                ele.getElementsByClass("info")[0].run {
                    pro.name = getElementsByClass("name").text()
                    getElementsByClass("price-discount")[0].run {
                        try {
                            pro.price = getElementsByClass("price-discount__price").text().filter { it.isDigit() }.toInt()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        try {
                            val promotePercent = getElementsByClass("price-discount__discount").text().filter { it.isDigit() }.toInt()
                            pro.list_price = pro.price * (100+promotePercent)/100
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                listItem.add(pro)
            }
            return listItem
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}