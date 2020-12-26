package com.example.tipee.screen.productdetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityProductDetailBinding
import com.example.tipee.screen.main.PlaceHolderActivity
import com.example.tipee.screen.productdetail.adapter.ImageAdapter
import com.example.tipee.widget.HtmlActivity
import com.example.tipee.widget.ShopView
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class ProductDetailActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ProductDetailActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityProductDetailBinding
    override fun getViewBinding(): View {
        mBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {

    }

    override fun configViews() {
        loadDetailProduct()
    }

    private fun loadDetailProduct(){
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rvImageDetail)
        mBinding.rvImageDetail.apply {
            adapter = ImageAdapter(object : ImageAdapter.OnViewClickListener{
                override fun onViewClick() {
                    PlaceHolderActivity.start(this@ProductDetailActivity)
                }
            })
        }
        mBinding.shopView.setOnShopClickListener(object : ShopView.OnShopViewClickListener {
            override fun onFollowClick(shopId: String) {
                Toast.makeText(this@ProductDetailActivity, "follow shop", Toast.LENGTH_SHORT).show()
            }

            override fun onShopDetailClick(shopId: String) {
                Toast.makeText(this@ProductDetailActivity, "detail shop clicked", Toast.LENGTH_SHORT).show()
            }
        })
        mBinding.shopView.loadShopData()
        val htmlString = "<span class='description-separate'></span>Có chức năng lọc Anion<br />\nMáy có các chế độ ngủ & chế độ tự động<br />\nQuạt tạo 3 tốc độ gió và có điều khiển từ xa<br />\nCông suất hoạt động đạt 40W, tiết kiệm năng lượng<br />\nLọc sạch đến 90% bụi, khói, các hạt bụi siêu mịn PM 2,5 và bụi nhỏ kích thước 3µm<br /><p><em><strong>Máy lọc không khí Kachi MK-195</strong></em> có thiết kế hình khối chắc chắn, nhỏ gọn, với màu đen trắng trang nhã, tăng tính thẩm mỹ cho mọi không gian sử dụng</p>\n<p><img src=\"https://salt.tikicdn.com/ts/product/47/f7/ec/470af8b8adc94874a85280bc6dc23a75.jpg\" /></p>\n<p> </p>\n<p><em>Công suất 40W,</em> lọc không khí tối ưu trong các không gian có diện tích ~35m2</p>\n<p><em><strong>Máy lọc không khí Kachi MK-195</strong></em> trang bị bộ lọc <em>HEPA</em> 3 lớp gồm có màng lọc bụi thô, màng lọc than hoạt tính và màng lọc HEPA, giúp loại bỏ dễ dàng các bụi bẩn, vi khuẩn, virus, khử mùi, diệt khuẩn, trả lại cho bạn không khí trong lành, tinh khiết:</p>\n<p><img src=\"https://salt.tikicdn.com/ts/product/96/ea/9f/fc625965d155cf9b71f2678c56db4b17.jpg\" /></p>\n<p> </p>\n<p>+ Bộ lọc thô lọc bụi kích thước lớn</p>\n<p>+ Màng lọc Hepa loại bỏ đến 90% các tạp chất có trong không khí với kích thước lọc siêu nhỏ đến 0.3 micro.</p>\n<p>+ Màng lọc than hoạt tính giúp khử mùi hiệu quả cho không khí, không khí trở nên thoáng đãng dễ chịu hơn.</p>\n<p>Bảng điều khiển cảm ứng mượt mà kèm màn hình hiển thị sắc nét, cho người sử dụng tiện quan sát và tùy chỉnh</p>\n<p><img src=\"https://salt.tikicdn.com/cache/w750/ts/product/4d/f2/12/29f9c9dc25f07551edcb64578445b4ef.jpg\" /></p>\n<p> </p>\n<p>Chế độ ngủ, tự động, tốc độ quạt 3 tốc độ từ nhẹ đến mạnh cho bạn lựa chọn theo nhu cầu, lọc không khí hiệu quả hơn. Chế độ UV/ion âm lọc không khí trong sạch, tốt cho sức khỏe hơn.</p>\n<p><strong><em>Máy lọc không khí Kachi MK-195</em></strong> có đèn báo cho biết chỉ số chất lượng của không khí. Sau 30ph vận hành, tùy theo mức độ không khí trong môi trường, đền báo sẽ có màu đỏ, xanh là hoặc xanh dương. Khi chỉ số chất lượng không khí tuyệt vời, đèn báo sẽ có màu xanh dương. Khi chỉ số chất lượng không khí ở mức trung bình, đèn báo sẽ có màu xanh lá và nếu chỉ số chất lượng không khí kém đén báo sẽ là màu đỏ.</p>\n<p><img src=\"https://salt.tikicdn.com/cache/w750/ts/product/18/64/83/57d62851c2e7c7d92e67ff0241ddb433.jpg\" /></p>\n<p><img src=\"https://salt.tikicdn.com/cache/w750/ts/product/f2/60/54/340b866a950afff75dba51044daabc5c.jpg\" /></p>\n<p> </p>\n<p><em><strong>Máy lọc không khí Kachi</strong></em> đạt tốc độ lọc <em>200-480m3/h</em>, có các tính năng hẹn giờ lên đến 8 tiếng gúp người dùng thoải mái sử dụng thiệt bị cả ban ngày hay ban đêm.  Máy hoạt động êm dịu, <em>độ ồn ≤55dB</em> không gây ra tiếng ồn khó chịu cho người dùng.  Có điều khiển từ xa, người dùng không cần tiếp cận gần vẫn điều khiển máy dễ dàng.</p>\n<p><img src=\"https://salt.tikicdn.com/cache/w750/ts/product/b5/6e/33/aed40358c97bb8091ac963cb692549eb.jpg\" /></p>\n<p><img src=\"https://salt.tikicdn.com/ts/product/c3/ae/04/751c6db2ebcfaec9227d3b1351ddfd0e.jpg\" /></p>\n<p> </p>\n<p><em>Máy làm từ chất liệu HIPS</em> nhẹ nhàng, thuận tiện cho bạn di chuyển đến bất cứ không gian nào bạn muốn.</p>\n<p><strong>THÔNG SỐ KỸ THUẬT</strong></p>\n<p><em>Model: MK-195</em></p>\n<p>Chất liệu: Nhựa HIPS</p>\n<p>Công suất: 40W</p>\n<p>Điện áp: 220V/50-60Hz</p>\n<p>Lưu lượng gió: 200 ~ 480m3/h</p>\n<p>Độ ồn: ≤55 dB</p>\n<p>Khối lượng (N.w): 4.3Kg</p>\n<p>Khối lượng (G.w): 5.2kg</p>\n<p>Kích thước sản phẩm: 33.5× 21× 56 cm</p>\n<p>Kích thước đóng thùng: 40.6 ×25× 58.7cm</p>\n<p>Xuất xứ thương hiệu: Việt Nam</p>\n<p style=\"text-align: center;\"><iframe src=\"//www.youtube.com/embed/YNfojfrXCYw\" width=\"560\" height=\"314\" allowfullscreen=\"allowfullscreen\"></iframe></p><p>Giá sản phẩm trên Tiki đã bao gồm thuế theo luật hiện hành. Tuy nhiên tuỳ vào từng loại sản phẩm hoặc phương thức, địa chỉ giao hàng mà có thể phát sinh thêm chi phí khác như phí vận chuyển, phụ phí hàng cồng kềnh, ...</p>"
        //mBinding.tvShortDescription.setHtml(htmlString, HtmlHttpImageGetter(mBinding.tvShortDescription, "", true))
        mBinding.viewMore.setOnClickListener {
            HtmlActivity.start(this, "Thông tin & Giới thiệu", htmlString)
        }
    }
}