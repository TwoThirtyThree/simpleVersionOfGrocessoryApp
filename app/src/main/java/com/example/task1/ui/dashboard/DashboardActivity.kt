// File: DashboardActivity.kt
package com.example.task1.ui.dashboard

import DashboardViewModel
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.task1.R
import com.example.task1.data.models.Banner
import com.example.task1.data.models.Items
import com.example.task1.data.models.Results
import com.example.task1.ui.adapters.BannerAdapter
import com.example.task1.ui.adapters.ItemsAdapter
import com.example.task1.utils.CartManager

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerviewForProduct: RecyclerView
    private lateinit var cartIcon: ImageView
    private lateinit var cartQuantityTextView: TextView
    private lateinit var cartManager: CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_screen)

        initView()
        fetchAllData()
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

    private fun fetchAllData() {
        lifecycleScope.launch {
            dashboardViewModel.fetchAllData()
                .observe(this@DashboardActivity) { result ->
                    when (result) {
                        is Results.Success -> {
                            val data = result.data
                            setupBanners(data.banners)
                            setupItems(data.items)
                        }
                        is Results.Failure -> {
                            showPopup(result.message, result.title)
                            Log.e("DashboardActivity", "Error fetching data: ${result.message}")
                        }
                    }
                }
        }
    }

    private fun setupBanners(banners: List<Banner>) {
        viewPager.adapter = BannerAdapter(banners)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun setupItems(items: List<Items>) {
        recyclerviewForProduct.layoutManager = GridLayoutManager(this, 2)
        val itemsAdapter = ItemsAdapter(items, object : ItemsAdapter.OnCartClickListener {
            override fun cartClick(position: Int) {
                cartManager.onCartClick(position)
            }

            override fun quantityIncrease(position: Int) {
                cartManager.onQuantityIncrease(position)
            }

            override fun quantityDecrease(position: Int) {
                cartManager.onQuantityDecrease(position)
            }
        })
        recyclerviewForProduct.adapter = itemsAdapter

        // Initialize CartManager with the adapter
        cartManager = CartManager(this, itemsAdapter, cartQuantityTextView)
    }

    private fun showPopup(title: String, message: String) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)

        dialogTitle.text = title
        dialogMessage.text = message

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
