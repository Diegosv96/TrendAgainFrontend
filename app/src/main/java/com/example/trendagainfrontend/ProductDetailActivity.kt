package com.example.trendagainfrontend

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.trendagainfrontend.data.data.model.Product

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra("product") as? Product

        if (product != null) {
            val imageView = findViewById<ImageView>(R.id.ivProductImage)
            val nameView = findViewById<TextView>(R.id.tvProductName)
            val priceView = findViewById<TextView>(R.id.tvProductPrice)
            val btnBuy = findViewById<Button>(R.id.btnBuy)
            val btnFavorite = findViewById<Button>(R.id.btnFavorite)

            nameView.text = product.name
            priceView.text = "Precio: ${product.price} €"
            val imageUrl = product.productImages.firstOrNull()?.imageUrl
            Glide.with(this).load(imageUrl).into(imageView)

            btnBuy.setOnClickListener {
                Toast.makeText(this, "Comprado ${product.name}", Toast.LENGTH_SHORT).show()
            }

            btnFavorite.setOnClickListener {
                Toast.makeText(this, "${product.name} añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
        } else {
            finish()
        }
    }
}
