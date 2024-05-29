package com.example.task1.ui.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.task1.R

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var detailsProductNameTextView : TextView
    private lateinit var detailsProductDescriptionTextView : TextView
    private lateinit var detailsProductPriceTextView : TextView
    private lateinit var detailsQuantityTextView : TextView
    private lateinit var detailsDiscountTextView :TextView
    private lateinit var detailsProductImageView :ImageView
    private lateinit var detaislAddToCartBtn : Button
    private lateinit var name: String
    private lateinit var description: String
    private lateinit var price: String
    private lateinit var imageUrl: String
    private var quantity: Int = 0
    private var discount: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        initViews()
        intentReciever()
        setIntentDataInViews()

    }



    private fun initViews() {
        detailsProductNameTextView = findViewById(R.id.textView_details_product_name)
        detailsProductDescriptionTextView = findViewById(R.id.textView_details_product_description)
        detailsProductPriceTextView = findViewById(R.id.textView_details_product_price)
        detailsQuantityTextView = findViewById(R.id.detail_squantity_in_numbers_textview)
        detailsDiscountTextView = findViewById(R.id.imageview_details_discount)
        detailsProductImageView = findViewById(R.id.details_product_imageview)
        detaislAddToCartBtn = findViewById(R.id.details_add_to_cart_button)
    }
    private fun intentReciever() {

         name = intent.getStringExtra("name").toString()
         description = intent.getStringExtra("description").toString()
         price = intent.getStringExtra("price").toString()
         imageUrl = intent.getStringExtra("imageUrl").toString()
         quantity = intent.getIntExtra("quantity", 0)
         discount = intent.getIntExtra("discount", 0)


    }
    private fun setIntentDataInViews() {
        detailsProductNameTextView.text = name
        detailsProductDescriptionTextView.text = description
        detailsProductPriceTextView.text = price
        detailsQuantityTextView.text = quantity.toString()
        detailsDiscountTextView.text = "$discount% OFF"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.cart)
            .error(androidx.core.R.drawable.ic_call_decline)
            .into(detailsProductImageView)
    }


}
