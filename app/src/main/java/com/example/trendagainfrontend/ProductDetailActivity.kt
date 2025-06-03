package com.example.trendagainfrontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.trendagainfrontend.data.data.api.RetrofitClient
import com.example.trendagainfrontend.data.data.model.Category
import com.example.trendagainfrontend.data.data.model.Favorite
import com.example.trendagainfrontend.data.data.model.Product
import com.example.trendagainfrontend.data.data.model.ProductImage
import com.example.trendagainfrontend.data.data.model.Transaction
import com.example.trendagainfrontend.data.data.model.User
import kotlinx.coroutines.launch
import java.util.Date

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // 1. Referencias UI
        val btnBack: Button = findViewById(R.id.btnBack)
        val ivImagen: ImageView = findViewById(R.id.ivThumbnail)
        val tvNombre: TextView = findViewById(R.id.tvName)
        val tvPrecio: TextView = findViewById(R.id.tvPrice)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val btnComprar: Button = findViewById(R.id.btnComprar)
        val btnFavorito: Button = findViewById(R.id.btnFavorito)

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName: String = sharedPrefs.getString("user_name", "").toString()

        // 2. Botón ATRÁS
        btnBack.setOnClickListener {
            finish()
        }

        // 3. Leer datos del Intent
        val productId = intent.getIntExtra("product_id", -1)
        val productName = intent.getStringExtra("product_name") ?: ""
        val productPrice = intent.getDoubleExtra("product_price", 0.0)
        val productDescription = intent.getStringExtra("product_description") ?: ""
        val productImageUrl = intent.getStringExtra("product_image_url") ?: ""

        // 4. Mostrar datos en pantalla
        tvNombre.text = productName
        tvPrecio.text = "€${"%.2f".format(productPrice)}"
        tvDescripcion.text = productDescription

        // 5. Cargar imagen con Glide
        Glide.with(this)
            .load(productImageUrl)
            .centerCrop()
            .into(ivImagen)

        // 6. Comprar
        btnComprar.setOnClickListener {
            lifecycleScope.launch {
                val usuario = obtenerUsuarioActual(userName)
                val producto = obtenerProductActual(productId)

                if (usuario != null && producto != null) {
                    val transaction = Transaction(
                        buyer = usuario,
                        product = producto,
                        amount = productPrice,
                        transactionDate = Date()
                    )
                    comprar(transaction)
                } else {
                    Toast.makeText(this@ProductDetailActivity, "No se pudo obtener usuario o producto", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 7. Marcar como favorito
        btnFavorito.setOnClickListener {
            lifecycleScope.launch {
                val usuario = obtenerUsuarioActual(userName)
                val producto = obtenerProductActual(productId)

                if (usuario != null && producto != null) {
                    val favorite = Favorite(
                        user = usuario,
                        product = producto,
                        createdAt = Date()
                    )
                    marcarComoFavorito(favorite)
                } else {
                    Toast.makeText(this@ProductDetailActivity, "No se pudo obtener usuario o producto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun obtenerUsuarioActual(name: String): User? {
        return try {
            val response = RetrofitClient.apiService.getUserByUsername(name)
            if (response.isSuccessful) {
                response.body()
            } else null
        } catch (e: Exception) {
            Toast.makeText(
                this@ProductDetailActivity,
                "Error al obtener usuario: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    suspend fun obtenerProductActual(productId: Int): Product? {
        return try {
            val response = RetrofitClient.apiService.getProduct(productId)
            if (response.isSuccessful) {
                response.body()
            } else null
        } catch (e: Exception) {
            Toast.makeText(
                this@ProductDetailActivity,
                "Error al obtener producto: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    private fun comprar(transaction : Transaction) {

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.createTransaction(transaction)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "¡Compra realizada con éxito!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@ProductDetailActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Error al comprar: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Error en la petición: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun marcarComoFavorito(favorite : Favorite) {

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.createFavorite(favorite)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "¡Producto marcado como favorito!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@ProductDetailActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Error al marcar favorito: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Error en la petición: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
