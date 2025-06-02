// ProductsAdapter.kt
package com.example.trendagainfrontend.data.data.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendagainfrontend.ProductDetailActivity
import com.example.trendagainfrontend.R

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String
)

class ProductsAdapter(
    private val context: Context,
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvName.text = product.name
        holder.tvPrice.text = String.format("$%.2f", product.price)

        // Usando Glide (o tu librería de carga de imágenes) para la miniatura
        Glide.with(context)
            .load(product.imageUrl)
            .centerCrop()
            .into(holder.ivThumbnail)

        // Listener para abrir el detalle al hacer clic
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                putExtra("product_name", product.name)
                putExtra("product_price", product.price)
                putExtra("product_description", product.description)
                putExtra("product_image_url", product.imageUrl)
            }
            context.startActivity(intent)
        }
    }
}
