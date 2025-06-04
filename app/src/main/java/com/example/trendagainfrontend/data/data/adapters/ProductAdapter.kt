// src/main/java/com/example/trendagainfrontend/data/data/adapters/ProductAdapter.kt
package com.example.trendagainfrontend.data.data.adapters


import android.content.Context

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendagainfrontend.ProductDetailActivity
import com.example.trendagainfrontend.data.data.model.Product
import com.example.trendagainfrontend.databinding.ItemProductBinding
import com.example.trendagainfrontend.ProductDetailActivity


class ProductAdapter(
    private val context: Context,          // <-- Inyectamos el Context
    private val products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // 1. Rellenar los datos del item (nombre, precio, imagen)
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "€${"%.2f".format(product.price)}"

            // Convertimos la primera imagen de `product.productImages` (si existe)
            val imageUrl = product.productImages.firstOrNull()?.imageUrl
            Glide.with(binding.ivProductImage.context)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivProductImage)

            // 2. Listener para abrir ProductDetailActivity
            binding.root.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("product_id", product.id)
                    putExtra("product_name", product.name)
                    putExtra("product_price", product.price)
                    putExtra("product_description", product.description)
                    // En tu modelo original quizá tengas `product.productImages[0].imageUrl`
                    putExtra("product_image_url", imageUrl ?: "")
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
