// DashBoardScreen.kt
package com.example.task1.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.task1.*
import com.example.task1.ui.adapters.BannerAdapter
import com.example.task1.ui.adapters.ProductAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity(), ProductAdapter.OnCartClickListener {
    private lateinit var viewPager: ViewPager2
    lateinit var dashboardViewModel: DashBoardViewModel
    private lateinit var tabLayout: TabLayout
    lateinit var recyclerViewForProduct: RecyclerView
    private lateinit var cartIcon: ImageView
    lateinit var cartQuantityTextView: TextView
   var totalQuantityInCart: Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_screen)
        initView()
        fetchBanners()
        fetchProducts()
    }

    private fun initView() {
        dashboardViewModel = ViewModelProvider(this, DashboardViewModelFactory(application = application)).get(
            DashBoardViewModel::class.java)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        recyclerViewForProduct = findViewById(R.id.recylicview_for_product)
        cartIcon = findViewById(R.id.cart_icon)
        cartQuantityTextView = findViewById(R.id.cart_quantity_textView)
    }

    private fun fetchBanners() {
        GlobalScope.launch(Dispatchers.Main) {
            val banners = dashboardViewModel.fetchBanners()
            banners.observe(this@DashboardActivity) { bannerList ->
                viewPager.adapter = BannerAdapter(bannerList)
                TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
            }
        }
    }

    private fun fetchProducts() {
        GlobalScope.launch(Dispatchers.Main) {
            val products = dashboardViewModel.fetchProducts()
            products.observe(this@DashboardActivity) { productList ->
                recyclerViewForProduct.layoutManager = GridLayoutManager(this@DashboardActivity, 2)
                val adapter = ProductAdapter(productList, this@DashboardActivity)
                recyclerViewForProduct.adapter = adapter
            }
        }
    }

    override fun CartClick(position: Int) {
        val product = (recyclerViewForProduct.adapter as ProductAdapter).productList[position]
        if (product.quantity > 0) {
            product.quantity -= 1
            totalQuantityInCart++
            updateProductListUI(position)


            updateCartIcon()
        } else {
            Toast.makeText(this, "Product is out of stock", Toast.LENGTH_SHORT).show()
        }
    }

    override fun QuantityIncrease(position: Int) {
        val product = (recyclerViewForProduct.adapter as ProductAdapter).productList[position]
        if (product.quantity > 0) {
            product.quantity -= 1
            totalQuantityInCart++
            updateProductListUI(position)


            updateCartIcon()
        } else {
            Toast.makeText(this, "No more stock available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun QuantityDecrease(position: Int) {
        if (totalQuantityInCart > 0) {
            totalQuantityInCart--
            updateCartIcon()

            updateProductListUI(position)
        } else {
            Toast.makeText(this, "Cannot decrease quantity further", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProductListUI(position: Int) {

        (recyclerViewForProduct.adapter as ProductAdapter).notifyItemChanged(position)
    }

    private fun updateCartIcon() {
            cartQuantityTextView.text = totalQuantityInCart.toString()
        Log.d("icon", "Total quantity in cart: $totalQuantityInCart")
    }
}
