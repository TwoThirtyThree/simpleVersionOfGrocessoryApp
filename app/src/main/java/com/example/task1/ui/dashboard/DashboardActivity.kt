package com.example.task1.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.task1.*
import com.example.task1.data.models.Results

import com.example.task1.ui.adapters.BannerAdapter
import com.example.task1.ui.adapters.ItemsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity(), ItemsAdapter.OnCartClickListener {
    private lateinit var viewPager: ViewPager2
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerviewForProduct: RecyclerView
    private lateinit var cartIcon: ImageView
    private lateinit var cartQuantityTextView: TextView
   private var totalQuantityInCart: Int = 0



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_screen)

        initView()
        fetchBanners()
        fetchItems()
    }

    private fun initView() {
        val factory = DashboardViewModelFactory(application)
        dashboardViewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        recyclerviewForProduct = findViewById(R.id.recylicview_for_product)
        cartIcon = findViewById(R.id.cart_icon)
        cartQuantityTextView = findViewById(R.id.cart_quantity_textView)
    }

    private fun fetchBanners() {
        lifecycleScope.launch {
            dashboardViewModel.fetchBanners().observe(this@DashboardActivity) { result ->
                when (result) {
                    is Results.Success -> {
                        val bannerList = result.data
                        viewPager.adapter = BannerAdapter(bannerList)
                        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
                    }
                    is Results.Failure -> {
                        showPopup(result.message, result.title)
                        Log.e("DashboardActivity", "Error fetching banners: } - ${result.message}")
                    }
                }
            }
        }
    }

    private fun fetchItems() {
        lifecycleScope.launch {
            dashboardViewModel.fetchItems().observe(this@DashboardActivity) { result ->
                when (result) {
                    is Results.Success -> {
                        val productList = result.data
                        recyclerviewForProduct.layoutManager = GridLayoutManager(this@DashboardActivity, 2)
                        recyclerviewForProduct.adapter = ItemsAdapter(productList, this@DashboardActivity)
                    }
                    is Results.Failure -> {
                        showPopup( result.message, result.title)
                        Log.e("DashboardActivity", "Error fetching items: } - ${result.message}")
                    }
                }
            }
        }
    }

    private fun showPopup(title: String, message: String) {
        // Inflate the custom dialog view
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)


        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)


        dialogTitle.text = title
        dialogMessage.text = message

        // Create and show the AlertDialog
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }


    override fun cartClick(position: Int) {
        val product = (recyclerviewForProduct.adapter as ItemsAdapter).productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
            updateProductListUI(position)


            updateCartIcon()
        } else {
            Toast.makeText(this, "Product is out of stock", Toast.LENGTH_SHORT).show()
        }
    }

    override fun quantityIncrease(position: Int) {
        val product = (recyclerviewForProduct.adapter as ItemsAdapter).productList[position]
        if (product.maxQty > 0) {
            product.maxQty -= 1
            totalQuantityInCart++
            updateProductListUI(position)


            updateCartIcon()
        } else {
            Toast.makeText(this, "No more stock available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun quantityDecrease(position: Int) {
        if (totalQuantityInCart > 0) {
            totalQuantityInCart--
            updateCartIcon()

            updateProductListUI(position)
        } else {
            Toast.makeText(this, "Cannot decrease quantity further", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProductListUI(position: Int) {

        (recyclerviewForProduct.adapter as ItemsAdapter).notifyItemChanged(position)
    }

    private fun updateCartIcon() {
            cartQuantityTextView.text = totalQuantityInCart.toString()
        Log.d("icon", "Total quantity in cart: $totalQuantityInCart")
    }


}
